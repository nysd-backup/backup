/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import org.coder.alpha.framework.registry.ServiceLocator;
import org.coder.alpha.framework.transaction.TransactionContext;
import org.coder.alpha.framework.transaction.TxVerifier;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * ExternalTransactionContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ExternalTransactionContext extends TransactionContext{
	
	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}

	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		return ServiceLocator.getService(TxVerifier.class);
	}

}
