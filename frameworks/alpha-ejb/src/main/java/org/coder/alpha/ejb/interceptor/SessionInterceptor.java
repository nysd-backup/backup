/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.ejb.interceptor;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.coder.alpha.message.context.ErrorMessageHandler;
import org.coder.alpha.message.context.MessageContext;

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
		MessageContext context = MessageContext.getCurrentInstance();		
		if(context == null){
			return invokeRoot(ic);
		}else {
			return invoke(ic);
		}
	
	}
	
	/**
	 * Process at the top level.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected Object invokeRoot(InvocationContext ic) throws Exception{
		MessageContext context = createContext();
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
		}
	}
	
	/**
	 * Process at the except top level.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected Object invoke(InvocationContext ic) throws Exception{
		return proceed(ic);
	}
	
	/**
	 * Initialize the transaction.
	 * @param ic
	 */
	protected void initialize(InvocationContext ic,MessageContext sc){		
		sc.setMessageHandler(new ErrorMessageHandler());
	}
	
	/**
	 * Handles the exception
	 * @param e
	 * @param context
	 * @return
	 */
	protected Object handleException(Object[] parameters, Class<?> returnType ,Exception e,MessageContext context)throws Exception{
		throw e;
	}
	
	/**
	 * Edits the response.
	 * @param invocationSource the source
	 * @param returnValue the returnValue
	 * @param context the context
	 * @return the edited returnValue
	 */
	protected Object editResponse(Object returnValue, MessageContext context){
		return returnValue;
	}
	
	/**
	 * @return the alpha.domain context
	 */
	protected MessageContext createContext(){
		return new MessageContext();
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
