/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction.consecutive;

import org.coder.alpha.framework.registry.ServiceLocator;
import org.coder.alpha.framework.registry.UnifiedComponentFinder;
import org.coder.alpha.framework.transaction.TransactionContext;
import org.coder.alpha.framework.transaction.TxVerifier;


/**
 * ConsecutiveTxContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConsecutiveTxContext extends TransactionContext{

	private boolean rollbackOnly = false;
	
	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		UnifiedComponentFinder finder = ServiceLocator.getComponentFinder();
		return finder.getTxVerifier();
	}

}
