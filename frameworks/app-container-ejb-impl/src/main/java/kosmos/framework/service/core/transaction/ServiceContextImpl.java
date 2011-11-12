/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import kosmos.framework.service.core.transaction.InternalUnitOfWork;
import kosmos.framework.service.core.transaction.TransactionManagingContext;

/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceContextImpl extends TransactionManagingContext{

	/**
	 * @see kosmos.framework.service.core.transaction.TransactionManagingContext#createInternalUnitOfWork()
	 */
	@Override
	protected InternalUnitOfWork createInternalUnitOfWork(){
		return new NamedInternalUnitOfWork();
	}
	
}
