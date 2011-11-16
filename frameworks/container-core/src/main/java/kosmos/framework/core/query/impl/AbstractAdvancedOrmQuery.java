/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import javax.persistence.LockModeType;

import kosmos.framework.core.query.AdvancedOrmQuery;
import kosmos.framework.jpqlclient.api.PersistenceHints;
import kosmos.framework.jpqlclient.api.orm.JPAOrmCondition;
import kosmos.framework.jpqlclient.api.orm.JPAOrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmCondition;
import kosmos.framework.sqlclient.api.orm.OrmQuery;


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
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#enableNoDataError()
	 */		
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q enableNoDataError() {
		delegate.enableNoDataError();
		return (Q)this;
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
		if(delegate instanceof JPAOrmQuery){
			((JPAOrmQuery<T>)delegate).setHint(key, value);
		}else{
			throw new UnsupportedOperationException();
		}
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setLockMode(LockModeType lockModeType) {
		if(delegate instanceof JPAOrmQuery){
			((JPAOrmQuery<T>)delegate).setLockMode(lockModeType);
		}else{
			throw new UnsupportedOperationException();
		}
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
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks){
		return delegate.exists(pks);
	}

	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQuery#setCondition(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setCondition(OrmCondition<T> condition) {
		if(condition instanceof JPAOrmCondition){
			((JPAOrmQuery<T>)delegate).setCondition((JPAOrmCondition<T>)condition);
		}else{
			throw new UnsupportedOperationException();
		}
		return (Q)this;
	}

}
