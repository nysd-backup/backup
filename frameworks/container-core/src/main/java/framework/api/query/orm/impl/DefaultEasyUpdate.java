/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm.impl;

import java.util.List;

import framework.api.query.orm.EasyUpdate;
import framework.api.query.orm.impl.AbstractAdvancedOrmUpdate;
import framework.sqlclient.api.orm.OrmUpdate;

/**
 * EasyUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultEasyUpdate<T> extends AbstractAdvancedOrmUpdate<T> implements EasyUpdate<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultEasyUpdate(OrmUpdate<T> delegate) {
		super(delegate);
	}

	/**
	 * @see framework.api.query.orm.EasyUpdate#set(java.lang.String[])
	 */
	@Override
	public EasyUpdate<T> set(String... setString) {
		delegate.set(setString);
		return this;
	}

	/**
	 * @see framework.api.query.orm.EasyUpdate#filter(java.lang.String)
	 */
	@Override
	public EasyUpdate<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see framework.api.query.orm.EasyUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int execute(List<Object> set, Object... params) {		
		return delegate.execute(set, params);
	}

}
