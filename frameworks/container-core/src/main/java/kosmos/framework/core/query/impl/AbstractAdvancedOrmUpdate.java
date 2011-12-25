/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import kosmos.framework.core.query.AdvancedOrmUpdate;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;

/**
 * AbstractAdvancedOrmUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractAdvancedOrmUpdate<T> implements AdvancedOrmUpdate<T>{

	/** the delegating query */
	protected OrmUpdate<T> delegate;
	
	/**
	 * @param delegate the delegate to set
	 */
	public AbstractAdvancedOrmUpdate(OrmUpdate<T> delegate){
		this.delegate = delegate;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmUpdate#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  <Q extends AdvancedOrmUpdate<T>> Q setCondition(OrmQueryContext<T> condition) {
		delegate.setCondition(condition);
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Q extends AdvancedOrmUpdate<T>> Q setHint(String key, Object value){
		delegate.setHint(key, value);
		return (Q)this;
	}
	
}
