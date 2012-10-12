/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.consecutive;

import alpha.framework.domain.activation.AbstractEJBComponentFinder;
import alpha.framework.domain.activation.ServiceLocator;
import alpha.framework.domain.transaction.DomainContext;
import alpha.framework.domain.transaction.TxVerifier;

/**
 * ConsecutiveTxContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConsecutiveTxContext extends DomainContext{

	private boolean rollbackOnly = false;
	
	/**
	 * @see alpha.framework.domain.transaction.DomainContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @see alpha.framework.domain.transaction.DomainContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

	/**
	 * @see alpha.framework.domain.transaction.DomainContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		AbstractEJBComponentFinder finder = ServiceLocator.unwrap();
		return finder.getTxVerifier();
	}

}
