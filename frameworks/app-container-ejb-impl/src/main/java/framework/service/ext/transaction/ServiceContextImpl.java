/**
 * Copyright 2011 the original author
 */
package framework.service.ext.transaction;

import framework.service.core.transaction.TransactionManagingContext;
import framework.service.core.transaction.InternalUnitOfWork;

/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceContextImpl extends TransactionManagingContext{

	/**
	 * @see framework.service.core.transaction.TransactionManagingContext#createInternalUnitOfWork()
	 */
	@Override
	protected InternalUnitOfWork createInternalUnitOfWork(){
		return new NamedInternalUnitOfWork();
	}
	
}
