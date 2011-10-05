/**
 * Copyright 2011 the original author
 */
package framework.service.ext.transaction;

import org.aspectj.lang.ProceedingJoinPoint;

import framework.core.exception.application.BusinessException;
import framework.service.core.transaction.AbstractDefaultInterceptor;
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
public class DefaultInterceptor extends AbstractDefaultInterceptor<ProceedingJoinPoint>{

	/**
	 * @see framework.service.core.transaction.AbstractDefaultInterceptor#initializeContext()
	 */
	@Override
	protected TransactionManagingContext initializeContext() {
		ServiceContextImpl context = new ServiceContextImpl();
		context.initialize();
		return context;
	}

	/**
	 * @see framework.service.core.transaction.AbstractDefaultInterceptor#proceed(java.lang.Object)
	 */
	@Override
	protected Object proceed(ProceedingJoinPoint ic) throws Throwable {
		return ic.proceed();
	}

	/**
	 * @see framework.service.core.transaction.AbstractDefaultInterceptor#createBusinessException()
	 */
	@Override
	protected BusinessException createBusinessException() {
		return new BusinessException(null);
	}


}
