/**
 * Copyright 2011 the original author
 */
package alpha.framework.transaction.consecutive;

import alpha.framework.registry.ServiceLocator;
import alpha.framework.registry.UnifiedComponentFinder;
import alpha.framework.transaction.TransactionContext;
import alpha.framework.transaction.TxVerifier;

/**
 * ConsecutiveTxContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConsecutiveTxContext extends TransactionContext{

	private boolean rollbackOnly = false;
	
	/**
	 * @see alpha.framework.transaction.TransactionContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @see alpha.framework.transaction.TransactionContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

	/**
	 * @see alpha.framework.transaction.TransactionContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		UnifiedComponentFinder finder = ServiceLocator.getComponentFinder();
		return finder.getTxVerifier();
	}

}
