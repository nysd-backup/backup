/**
 * Copyright 2011 the original author
 */
package framework.service.core.transaction;

import framework.api.dto.ReplyMessage;
import framework.core.exception.application.BusinessException;

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
public abstract class AbstractDefaultInterceptor<T> {

	/**
	 * Invoke service.
	 * 
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	public Object around(T ic) throws Throwable {
		
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
	protected Object invoke(T ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(T ic) throws Throwable {
		
		TransactionManagingContext context = initializeContext();
		long startTime = System.currentTimeMillis();
		Throwable cause = null;
		Object retValue = null;
		try{
			proceed(ic);	
			
			//いずれかのトランザクションで失敗していればエラーとする。
			if(context.isAnyTransactionFailed()){
				BusinessException e = createBusinessException();
				e.setMessageList(context.getMessageList().toArray(new ReplyMessage[0]));				
				throw e;
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
	protected void terminate(long startTime,Throwable cause){
		
	}
	
	/**
	 * Proceed service.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected abstract Object proceed(T ic) throws Throwable;

}
