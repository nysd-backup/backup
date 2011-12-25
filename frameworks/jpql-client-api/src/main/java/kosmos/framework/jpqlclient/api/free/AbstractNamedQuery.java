/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.free.AbstractFreeQuery;


/**
 * NamedQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNamedQuery extends AbstractFreeQuery<NamedQuery> implements NamedQuery{
	
	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends NamedQuery> T setLockMode(LockModeType arg0) {
		delegate.setLockMode(arg0);
		return (T)this;
	}
	
}
