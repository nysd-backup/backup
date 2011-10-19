/**
 * Copyright 2011 the original author
 */
package framework.service.ext;

import framework.core.exception.BusinessException;
import framework.service.core.advice.AbstractInternalDefaultInterceptor;
import framework.service.core.transaction.TransactionManagingContext;
import framework.service.ext.exception.ApplicationException;
import framework.service.ext.transaction.ServiceContextImpl;

/**
 * The default interceptor.
 * 
 * @author yoshida-n
 * @version	created.
 */
public class MockInternalDefaultInterceptor extends AbstractInternalDefaultInterceptor{

	/**
	 * @see framework.service.core.advice.AbstractInternalDefaultInterceptor#createBusinessException()
	 */
	@Override
	protected BusinessException createBusinessException() {
		return new ApplicationException(null);
	}

	/**
	 * @see framework.service.core.advice.AbstractInternalDefaultInterceptor#initializeContext()
	 */
	@Override
	protected TransactionManagingContext initializeContext() {
		ServiceContextImpl context = new ServiceTestContextImpl();
		context.initialize();
		return context;
	}

}
