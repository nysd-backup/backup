/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.TransactionManagingContext;

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
public class InternalDefaultInterceptor implements InternalInterceptor {
	
	public static final String ANY_TRANSACTION_FAILED = "Any transaction is failed";

	/**
	 * @see kosmos.framework.service.core.advice.InternalInterceptor#around(kosmos.framework.service.core.advice.InvocationAdapter)
	 */
	@Override
	public Object around(InvocationAdapter ic) throws Throwable {
		
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
	protected Object invoke(InvocationAdapter ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(InvocationAdapter ic) throws Throwable {
	
		TransactionManagingContext context = ServiceLocator.createDefaultServiceContext();
		context.initialize();
		try{
			Object retValue = proceed(ic);	
			if(context.isAnyTransactionFailed()){
				throw afterError(retValue);	
			}
			return retValue;
		}catch(Throwable t){
			if ( t instanceof BusinessException ){
				BusinessException be = (BusinessException)t;
				be.setMessageList(context.getMessageArray());
			}
			throw t;
		}finally {
			context.release();
		}
	}
	
	/**
	 * Handles the business error.
	 * 
	 * @param retValue the value to return.
	 */
	protected BusinessException afterError(Object retValue){
		return new BusinessException(ANY_TRANSACTION_FAILED);
	}

	/**
	 * Proceed service.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object proceed(InvocationAdapter ic) throws Throwable{
		return ic.proceed();
	}

}
