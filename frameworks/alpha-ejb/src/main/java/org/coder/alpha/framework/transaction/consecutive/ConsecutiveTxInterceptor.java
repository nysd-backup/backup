/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction.consecutive;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.InvocationContext;

import org.coder.alpha.framework.advice.InternalPerfInterceptor;
import org.coder.alpha.framework.advice.DefaultInvocationAdapter;
import org.coder.alpha.framework.registry.EJBComponentFinder;
import org.coder.alpha.framework.registry.ServiceLocator;
import org.coder.alpha.framework.transaction.AbstractTxInterceptor;
import org.coder.alpha.framework.transaction.TransactionContext;



/**
 * ConsecutiveTxInterceptor.
 * <pre>
 * Don't add the message in autonomous transaction.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConsecutiveTxInterceptor extends AbstractTxInterceptor{

	@Resource
	private EJBContext sessionContext;

	/**
	 * @see org.coder.alpha.framework.transaction.AbstractTxInterceptor#invokeRoot(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invokeRoot(InvocationContext ic) throws Throwable {
		TransactionContext context = createContext();
		try{		
			context.initialize();		
			initialize(ic,context);
							
			Object returnValue = proceed(ic);
			if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
				sessionContext.setRollbackOnly();				
			}							
			return editResponse( returnValue, context);
		}catch(Throwable e){
			sessionContext.setRollbackOnly();
			return handleException(ic.getParameters(),ic.getMethod().getReturnType(),e,context);			
		}finally{				
			context.release();		
			terminate();
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
	protected Object handleException(Object[] parameters, Class<?> returnType ,Throwable e,TransactionContext context)throws Throwable{
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
		return new ConsecutiveTxContext();
	}

	/**
	 * @see org.coder.alpha.framework.transaction.AbstractTxInterceptor#invoke(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invoke(InvocationContext ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * @param ic
	 * @return
	 * @throws Exception
	 */
	protected Object proceed(InvocationContext ic) throws Throwable{
		try{
			EJBComponentFinder finder = ServiceLocator.getComponentFinder();
			InternalPerfInterceptor interceptor = finder.getInternaPerflInterceptor();
			if(interceptor.isEnabled()){
				return interceptor.around(new DefaultInvocationAdapter(ic));
			}else {
				return ic.proceed();
			}
		}catch(Exception e){
			wrapByAppException(e);
			return null;
		}
	}
	

	/**
	 * wrap by application exception if needed.
	 * 
	 * @param e 
	 * @throws Exception
	 */
	protected void wrapByAppException(Exception e)throws Exception{
		throw e;
	}

}
