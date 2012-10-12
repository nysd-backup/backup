/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import alpha.framework.domain.activation.ServiceLocator;
import alpha.framework.domain.transaction.DomainContext;

/**
 * StubServiceContextImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ServiceContextImpl extends DomainContext{
	
	/**
	 * @see alpha.framework.domain.transaction.DomainContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	/**
	 * @see alpha.framework.domain.transaction.DomainContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}

	/**
	 * @see alpha.framework.domain.transaction.DomainContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		return ServiceLocator.getService(TxVerifier.class);
	}

}
