/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import javax.persistence.LockModeType;



/**
 * NamedQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNamedReadQuery extends AbstractFreeReadQuery{
	
	/**
	 * Sets the Lock mode.
	 * @param arg0 lock mode
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractNamedReadQuery> T setLockMode(LockModeType arg0) {
		super.getParameter().setLockMode(arg0);
		return (T)this;
	}
	
}
