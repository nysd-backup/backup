/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.free.AbstractFreeQuery;
import kosmos.framework.sqlclient.api.free.FreeQuery;


/**
 * NamedQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNamedQuery extends AbstractFreeQuery<NamedQuery> implements NamedQuery{
	
	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends FreeQuery> T setHint(String arg0 , Object arg1){
		delegate.setHint(arg0, arg1);
		return (T)this;
	}
	
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
