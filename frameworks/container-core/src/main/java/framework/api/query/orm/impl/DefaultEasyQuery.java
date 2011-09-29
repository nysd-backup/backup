/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm.impl;

import java.util.List;

import framework.api.query.orm.EasyQuery;
import framework.api.query.orm.impl.AbstractAdvancedOrmQuery;
import framework.sqlclient.api.orm.OrmQuery;

/**
 * EasyQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultEasyQuery<T> extends AbstractAdvancedOrmQuery<T> implements EasyQuery<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultEasyQuery(OrmQuery<T> delegate) {
		super(delegate);
	}

	/**
	 * @see framework.api.query.orm.EasyQuery#filter(java.lang.String)
	 */
	@Override
	public EasyQuery<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see framework.api.query.orm.EasyQuery#order(java.lang.String)
	 */
	@Override
	public EasyQuery<T> order(String orderString) {
		delegate.order(orderString);
		return this;
	}

	/**
	 * @see framework.api.query.orm.EasyQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params) {
		return delegate.list(params);
	}

	/**
	 * @see framework.api.query.orm.EasyQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params) {
		return delegate.single(params);
	}

	/**
	 * @see framework.api.query.orm.EasyQuery#findAny(java.lang.Object[])
	 */
	@Override
	public T findAny(Object... params) {
		return delegate.findAny(params);
	}

	/**
	 * @see framework.api.query.orm.EasyQuery#existsByAny(java.lang.Object[])
	 */
	@Override
	public boolean existsByAny(Object... params) {
		return findAny(params) != null;
	}

	/**
	 * @see framework.api.query.orm.EasyQuery#existsByList(java.lang.Object[])
	 */
	@Override
	public boolean existsByList(Object... params) {
		return !delegate.list(params).isEmpty();
	}	

}
