/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import javax.persistence.LockModeType;

import kosmos.framework.core.query.AdvancedOrmQuery;
import kosmos.framework.sqlclient.api.PersistenceHints;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;


/**
 * AbstractAdvancedOrmQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractAdvancedOrmQuery<T> implements AdvancedOrmQuery<T>{
	
	/** the delegating query */
	protected OrmQuery<T> delegate;

	/**
	 * @param delegate the delegate to set
	 */
	public AbstractAdvancedOrmQuery(OrmQuery<T> delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @return the delegate
	 */
	public OrmQuery<T> getDelegate(){
		return this.delegate;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#setPessimisticReadNoWait()
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setPessimisticReadNoWait(){
		setLockMode(LockModeType.PESSIMISTIC_READ);
		setHint(PersistenceHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setHint(String key, Object value){
		delegate.setHint(key, value);
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setLockMode(LockModeType lockModeType) {
		delegate.setLockMode(lockModeType);
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setMaxResults(int)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setFirstResult(int)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks){
		return delegate.find(pks);
	}

	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryContext)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setCondition(OrmQueryContext<T> condition) {
		delegate.setCondition(condition);
		return (Q)this;
	}

}
