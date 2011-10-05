/**
 * Copyright 2011 the original author
 */
package framework.service.ext.transaction;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import framework.core.exception.application.BusinessException;
import framework.service.core.transaction.AbstractDefaultInterceptor;
import framework.service.core.transaction.TransactionManagingContext;
import framework.service.ext.exception.EJBBusinessException;

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
public class DefaultInterceptor extends AbstractDefaultInterceptor<InvocationContext>{
	
	/**
	 * @see framework.service.core.transaction.AbstractDefaultInterceptor#around(java.lang.Object)
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		return super.around(ic);
	}

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
	protected Object proceed(InvocationContext ic) throws Throwable {
		return ic.proceed();
	}

	/**
	 * @see framework.service.core.transaction.AbstractDefaultInterceptor#createBusinessException()
	 */
	@Override
	protected BusinessException createBusinessException() {
		return new EJBBusinessException(null);
	}

}
