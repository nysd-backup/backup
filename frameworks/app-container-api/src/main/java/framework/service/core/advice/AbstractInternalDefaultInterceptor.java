/**
 * Copyright 2011 the original author
 */
package framework.service.core.advice;

import framework.api.dto.ReplyMessage;
import framework.core.exception.BusinessException;
import framework.service.core.transaction.ServiceContext;
import framework.service.core.transaction.TransactionManagingContext;

/**
 * The default interceptor.
 * 
 * <pre>
 * Creates the context and throws the BusinessException if the service is failed.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractInternalDefaultInterceptor implements InternalInterceptor {

	/**
	 * @see framework.service.core.advice.InternalInterceptor#around(framework.service.core.advice.ContextAdapter)
	 */
	@Override
	public Object around(ContextAdapter ic) throws Throwable {
		
		ServiceContext context = ServiceContext.getCurrentInstance();
		if(context == null){
			return invokeAtTopLevel(ic);
		}else {
			return invoke(ic);
		}
	}
	
	/**
	* Process at the except top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invoke(ContextAdapter ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(ContextAdapter ic) throws Throwable {
		
		TransactionManagingContext context = initializeContext();
		long startTime = System.currentTimeMillis();
		Throwable cause = null;
		Object retValue = null;
		try{
			proceed(ic);	
			
			//いずれかのトランザクションで失敗していればエラーとする。
			if(context.isAnyTransactionFailed()){
				throw createBusinessException();			
			}

		}catch(Throwable e){		
			cause = e;		
		}finally{
			try{
				terminate(startTime,cause);
			}finally{
				context.release();
			}
		}
		return retValue;
	}
	
	/**
	 * @return the BusinessException
	 */
	protected abstract BusinessException createBusinessException();
	
	/**
	 * Initialized the context.
	 */
	protected abstract TransactionManagingContext initializeContext();
	
	/**
	 * Terminates the context.
	 * 
	 * @param startTime the start time
	 * @param cause the exception
	 */
	protected void terminate(long startTime,Throwable cause) throws Throwable{
		if(cause != null){
			if(cause instanceof BusinessException){
				BusinessException.class.cast(cause).setMessageList(ServiceContext.getCurrentInstance().getMessageList().toArray(new ReplyMessage[0]));
			}			
			throw cause;
		}
	}

	
	/**
	 * Proceed service.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object proceed(ContextAdapter ic) throws Throwable{
		return ic.proceed();
	}

}
