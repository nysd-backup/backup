/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.wrapper.free;

import javax.persistence.LockModeType;

import kosmos.framework.jpqlclient.api.free.NamedQuery;
import kosmos.framework.sqlclient.api.wrapper.free.AbstractFreeQuery;


/**
 * NamedQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNamedQuery extends AbstractFreeQuery<NamedQuery>{
	
	/**
	 * Sets the Lock mode.
	 * @param arg0 lock mode
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractNamedQuery> T setLockMode(LockModeType arg0) {
		delegate.setLockMode(arg0);
		return (T)this;
	}
	
}
