/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm.impl;

import kosmos.framework.sqlclient.api.orm.OrmUpdate;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;
import kosmos.framework.sqlclient.api.wrapper.orm.OrmUpdateWrapper;

/**
 * AbstractOrmUpdateWrapper.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractOrmUpdateWrapper<T> implements OrmUpdateWrapper<T>{

	/** the delegating query */
	protected OrmUpdate<T> delegate;
	
	/**
	 * @param delegate the delegate to set
	 */
	public AbstractOrmUpdateWrapper(OrmUpdate<T> delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmUpdateWrapper#getCurrentParams()
	 */
	@Override
	public OrmUpdateParameter<T> getCurrentParams(){
		return delegate.getCurrentParams();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmUpdateWrapper#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  <Q extends OrmUpdateWrapper<T>> Q setCondition(OrmUpdateParameter<T> condition) {
		delegate.setCondition(condition);
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmUpdateWrapper#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Q extends OrmUpdateWrapper<T>> Q setHint(String key, Object value){
		delegate.setHint(key, value);
		return (Q)this;
	}
	
}
