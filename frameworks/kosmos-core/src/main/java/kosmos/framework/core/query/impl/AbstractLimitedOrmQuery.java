/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import javax.persistence.LockModeType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kosmos.framework.core.query.LimitedOrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;


/**
 * AbstractLimitedOrmQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLimitedOrmQuery<T> implements LimitedOrmQuery<T>{
	
	/** the delegating query */
	protected OrmQuery<T> delegate;

	/**
	 * @param delegate the delegate to set
	 */
	public AbstractLimitedOrmQuery(OrmQuery<T> delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmQuery#getCurrentParams()
	 */
	@Override
	public OrmQueryParameter<T> getCurrentParams(){
		return delegate.getCurrentParams();
	}
	
	/**
	 * @return the delegate
	 */
	public OrmQuery<T> getDelegate(){
		return this.delegate;
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <Q extends LimitedOrmQuery<T>> Q setHint(String key, Object value){
		delegate.setHint(key, value);
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public <Q extends LimitedOrmQuery<T>> Q setLockMode(LockModeType lockModeType) {
		delegate.setLockMode(lockModeType);		
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setMaxResults(int)
	 */
	@Override
	public <Q extends LimitedOrmQuery<T>> Q setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setFirstResult(int)
	 */
	@Override
	public <Q extends LimitedOrmQuery<T>> Q setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.core.query.LimitedOrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks){
		return delegate.find(pks);
	}

	/**
	 * @see kosmos.framework.core.query.LimitedOrmQuery#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	public <Q extends LimitedOrmQuery<T>> Q setCondition(OrmQueryParameter<T> condition) {
		delegate.setCondition(condition);
		return (Q)this;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}
