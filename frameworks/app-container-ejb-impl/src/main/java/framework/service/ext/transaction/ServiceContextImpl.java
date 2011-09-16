/**
 * Use is subject to license terms.
 */
package framework.service.ext.transaction;

import framework.service.core.transaction.TransactionManagingContext;
import framework.service.core.transaction.InternalUnitOfWork;

/**
 * コンテキスト.
 *
 * @author yoshida-n
 * @version	created.
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
