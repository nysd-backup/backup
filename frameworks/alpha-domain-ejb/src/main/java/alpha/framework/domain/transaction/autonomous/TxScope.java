/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.autonomous;


/**
 * TxScope.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class TxScope {
	
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
