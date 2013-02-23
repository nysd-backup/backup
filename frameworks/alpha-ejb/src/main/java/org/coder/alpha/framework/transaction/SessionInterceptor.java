/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * SessionInterceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SessionInterceptor {
	
	@Resource
	private EJBContext sessionContext;
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Exception {	
		TransactionContext context = TransactionContext.getCurrentInstance();		
		if(context == null){
			return invokeRoot(ic);
		}else {
			return invoke(ic);
		}
	
	}
	
	/**
	 * Process at the except top level.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected Object invokeRoot(InvocationContext ic) throws Exception{
		TransactionContext context = createContext();
		try{		
			context.initialize();		
			initialize(ic,context);
							
			Object returnValue = proceed(ic);
			if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
				sessionContext.setRollbackOnly();				
			}							
			return editResponse( returnValue, context);
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			return handleException(ic.getParameters(),ic.getMethod().getReturnType(),e,context);			
		}finally{				
			context.release();		
			terminate();
		}
	}
	
	/**
	 * Proceeds the method.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected Object invoke(InvocationContext ic) throws Exception {
		NestedTransactionContext context = (NestedTransactionContext)TransactionContext.getCurrentInstance();
		boolean isTransactionBoundary = false;
		TransactionAttribute attribute = ic.getMethod().getDeclaringClass().getAnnotation(TransactionAttribute.class);
				
		if(attribute != null && TransactionAttributeType.REQUIRES_NEW == attribute.value()){
			isTransactionBoundary = true;
		}else {
			attribute = ic.getMethod().getAnnotation(TransactionAttribute.class);
			if(attribute != null && TransactionAttributeType.REQUIRES_NEW == attribute.value()){
				isTransactionBoundary = true;
			}
		}
		if(isTransactionBoundary){
			context.newTransactionScope();
		}
		try{		
			Object retValue = proceed(ic);
			if(isTransactionBoundary){
				finishTransactionBoundary(retValue);
			}
			return retValue;
		}finally{			
			//Synchronization 			
			if(isTransactionBoundary){
				try{
					if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
						sessionContext.setRollbackOnly();				
					}
				}finally{
					context.removeTransactionScope();
				}
			}
		}
	}
	
	/**
	 * Initialize the transaction.
	 * @param ic
	 */
	protected void initialize(InvocationContext ic,TransactionContext sc){		
	}
	
	/**
	 * Terminate the Transaction
	 */
	public void terminate() {		
	}
	
	/**
	 * Handles the exception
	 * @param e
	 * @param context
	 * @return
	 */
	protected Object handleException(Object[] parameters, Class<?> returnType ,Exception e,TransactionContext context)throws Exception{
		throw e;
	}
	
	/**
	 * Edits the response.
	 * @param invocationSource the source
	 * @param returnValue the returnValue
	 * @param context the context
	 * @return the edited returnValue
	 */
	protected Object editResponse(Object returnValue, TransactionContext context){
		return returnValue;
	}
	
	/**
	 * @return the alpha.domain context
	 */
	protected TransactionContext createContext(){
		return new NestedTransactionContext();
	}

	/**
	 * Transaction border event.
	 * @param retValue value to return
	 */
	protected void finishTransactionBoundary(Object retValue){	
	}
	
	/**
	 * @param ic
	 * @return
	 * @throws Exception
	 */
	protected Object proceed(InvocationContext ic) throws Exception{
		return ic.proceed();
	}

}
