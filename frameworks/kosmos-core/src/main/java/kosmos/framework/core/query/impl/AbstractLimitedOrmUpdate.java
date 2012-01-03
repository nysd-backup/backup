/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import kosmos.framework.core.query.LimitedOrmUpdate;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;

/**
 * AbstractLimitedOrmUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractLimitedOrmUpdate<T> implements LimitedOrmUpdate<T>{

	/** the delegating query */
	protected OrmUpdate<T> delegate;
	
	/**
	 * @param delegate the delegate to set
	 */
	public AbstractLimitedOrmUpdate(OrmUpdate<T> delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmUpdate#getCurrentParams()
	 */
	@Override
	public OrmUpdateParameter<T> getCurrentParams(){
		return delegate.getCurrentParams();
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmUpdate#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  <Q extends LimitedOrmUpdate<T>> Q setCondition(OrmUpdateParameter<T> condition) {
		delegate.setCondition(condition);
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Q extends LimitedOrmUpdate<T>> Q setHint(String key, Object value){
		delegate.setHint(key, value);
		return (Q)this;
	}
	
}
