/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;


/**
 * An unit of work.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalUnitOfWork {
	
	/** the flag represent transaction is rolled back. Never to recover.*/
	private boolean rollbackOnly = false;

	/**
	 * set the rollbackOnly true
	 */
	protected void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @return the rollbackOnly
	 */
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

}
