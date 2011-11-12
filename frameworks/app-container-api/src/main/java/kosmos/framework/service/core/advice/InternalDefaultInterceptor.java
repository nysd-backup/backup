/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

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
public abstract class InternalDefaultInterceptor implements InternalInterceptor {

	/**
	 * @see kosmos.framework.service.core.advice.InternalInterceptor#around(kosmos.framework.service.core.advice.InvocationAdapter)
	 */
	@Override
	public Object around(InvocationAdapter ic) throws Throwable {
		
		ServiceContext context = ServiceContext.getCurrentInstance();
		if(context.getCallStackLevel() <= 0){
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
	
		Object retValue = null;
	
		retValue = proceed(ic);	
	
		TransactionManagingContext context = TransactionManagingContext.class.cast(ServiceContext.getCurrentInstance());
		if(context.isAnyTransactionFailed()){
			afterError(retValue);	
		}
		return retValue;
	}
	
	/**
	 * Handles the business error.
	 * 
	 * @param retValue the value to return.
	 */
	protected void afterError(Object retValue){
		
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
