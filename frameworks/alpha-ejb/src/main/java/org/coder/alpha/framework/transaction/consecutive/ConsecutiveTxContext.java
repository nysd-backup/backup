/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction.consecutive;

import org.coder.alpha.framework.transaction.TransactionContext;


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
}
