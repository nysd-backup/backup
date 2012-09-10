/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.simple;

import alpha.framework.domain.transaction.ServiceContext;

/**
 * SimpleServiceContextImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SimpleServiceContextImpl extends ServiceContext{

	private boolean rollbackOnly = false;
	
	/**
	 * @see alpha.framework.domain.transaction.ServiceContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @see alpha.framework.domain.transaction.ServiceContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

}
