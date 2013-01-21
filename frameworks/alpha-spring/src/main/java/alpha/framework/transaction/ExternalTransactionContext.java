/**
 * Copyright 2011 the original author
 */
package alpha.framework.transaction;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import alpha.framework.registry.ServiceLocator;
import alpha.framework.transaction.TxVerifier;

/**
 * ExternalTransactionContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ExternalTransactionContext extends TransactionContext{
	
	/**
	 * @see alpha.framework.transaction.TransactionContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	/**
	 * @see alpha.framework.transaction.TransactionContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}

	/**
	 * @see alpha.framework.transaction.TransactionContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		return ServiceLocator.getService(TxVerifier.class);
	}

}
