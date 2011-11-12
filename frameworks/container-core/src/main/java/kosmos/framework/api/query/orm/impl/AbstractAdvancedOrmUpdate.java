/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.query.orm.impl;

import kosmos.framework.api.query.orm.AdvancedOrmUpdate;
import kosmos.framework.jpqlclient.api.orm.JPAOrmCondition;
import kosmos.framework.jpqlclient.api.orm.JPAOrmUpdate;
import kosmos.framework.sqlclient.api.orm.OrmCondition;
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
	 * @see kosmos.framework.api.query.orm.AdvancedOrmUpdate#setCondition(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  <Q extends AdvancedOrmUpdate<T>> Q setCondition(OrmCondition<T> condition) {
		if(condition instanceof JPAOrmCondition){
			((JPAOrmUpdate<T>)delegate).setCondition((JPAOrmCondition<T>)condition);
		}else{
			throw new UnsupportedOperationException();
		}
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Q extends AdvancedOrmUpdate<T>> Q setHint(String key, Object value){
		if(delegate instanceof JPAOrmUpdate){
			((JPAOrmUpdate<T>)delegate).setHint(key, value);
		}else{
			throw new UnsupportedOperationException();
		}
		return (Q)this;
	}
	
}
