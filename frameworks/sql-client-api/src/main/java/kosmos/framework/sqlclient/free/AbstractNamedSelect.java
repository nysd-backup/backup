/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import javax.persistence.LockModeType;



/**
 * NamedQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNamedSelect extends AbstractFreeSelect{
	
	/**
	 * Sets the Lock mode.
	 * @param arg0 lock mode
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractNamedSelect> T setLockMode(LockModeType arg0) {
		super.getParameter().setLockMode(arg0);
		return (T)this;
	}
	
}
