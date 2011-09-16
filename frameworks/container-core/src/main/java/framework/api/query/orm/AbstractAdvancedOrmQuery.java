/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import javax.persistence.LockModeType;

import framework.jpqlclient.api.JPAQueryHints;
import framework.jpqlclient.api.orm.JPAOrmCondition;
import framework.jpqlclient.api.orm.JPAOrmQuery;
import framework.sqlclient.api.orm.OrmCondition;
import framework.sqlclient.api.orm.OrmQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractAdvancedOrmQuery<T> implements AdvancedOrmQuery<T>{
	
	/** エンジン */
	protected OrmQuery<T> delegate;

	/**
	 * @param entityClass エンティティクラス
	 */
	public AbstractAdvancedOrmQuery(OrmQuery<T> delegate){
		this.delegate = delegate;
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
	 * @see framework.sqlclient.api.orm.OrmQuery#setProviderHint(java.lang.String, java.lang.Object)
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
	 * @see framework.sqlclient.api.orm.OrmQuery#setLockMode(javax.persistence.LockModeType)
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
	 * @see framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object)
	 */
	@Override
	public T find(Object... pks){
		return delegate.find(pks);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findWithLockNoWait(java.lang.Object)
	 */
	@Override
	public T findWithLockNoWait(Object... pks){
		return delegate.findWithLockNoWait(pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object)
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
	 * @see framework.api.query.orm.AdvancedOrmQuery#setPessimiticLock(int)
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setPessimiticLock(int timeout) {
		setLockMode(LockModeType.PESSIMISTIC_READ);
		setHint(JPAQueryHints.PESSIMISTIC_READ_TIMEOUT, timeout);
		return (Q)this;
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQuery#setPessimiticLockNoWait()
	 */
	@Override
	public <Q extends AdvancedOrmQuery<T>> Q setPessimiticLockNoWait() {
		setPessimiticLock(0);
		return (Q)this;
	}

}
