/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm.impl;

import javax.persistence.LockModeType;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import framework.api.query.orm.AdvancedOrmQuery;
import framework.jpqlclient.api.orm.JPAOrmCondition;
import framework.jpqlclient.api.orm.JPAOrmQuery;
import framework.sqlclient.api.orm.OrmCondition;
import framework.sqlclient.api.orm.OrmQuery;

/**
 * AbstractAdvancedOrmQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractAdvancedOrmQuery<T> implements AdvancedOrmQuery<T>{
	
	/** ORクエリ */
	protected OrmQuery<T> delegate;

	/**
	 * @param delegate the delegate to set
	 */
	public AbstractAdvancedOrmQuery(OrmQuery<T> delegate){
		this.delegate = delegate;
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#setHintString(java.lang.String)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setHintString(String hintValue){
		setHint(QueryHints.HINT,hintValue);
		return (Q)this;
	}
	
	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#setRefleshMode()
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setRefleshMode(){
		setHint(QueryHints.REFRESH,HintValues.TRUE);
		return (Q)this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#enableNoDataError()
	 */		
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q enableNoDataError() {
		delegate.enableNoDataError();
		return (Q)this;
	}
	
	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#setHint(java.lang.String, java.lang.Object)
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
	 * @see framework.api.query.orm.AdvancedOrmQuery#setLockMode(javax.persistence.LockModeType)
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
	 * @see framework.sqlclient.api.orm.OrmQuery#setMaxResults(int)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#setFirstResult(int)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks){
		return delegate.find(pks);
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks){
		return delegate.exists(pks);
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#setCondition(framework.sqlclient.api.orm.OrmCondition)
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

	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#setPessimisticRead()
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setPessimisticRead() {
		setLockMode(LockModeType.PESSIMISTIC_READ);
		return (Q)this;
	}

}
