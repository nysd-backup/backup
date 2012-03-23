/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm.impl;

import java.util.List;

import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.wrapper.orm.LightQuery;


/**
 * LightQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultLightQuery<T> extends AbstractOrmQueryWrapper<T> implements LightQuery<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultLightQuery(OrmQuery<T> delegate) {
		super(delegate);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightQuery#filter(java.lang.String)
	 */
	@Override
	public LightQuery<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightQuery#order(java.lang.String)
	 */
	@Override
	public LightQuery<T> order(String orderString) {
		delegate.order(orderString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params) {
		return delegate.list(params);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params) {
		return delegate.single(params);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapper#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return find(pks) != null;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightQuery#fetch(kosmos.framework.sqlclient.api.free.QueryCallback, java.lang.Object[])
	 */
	@Override
	public long fetch(QueryCallback<T> callback,Object... params) {
		return delegate.fetch(callback, params);
	}

}
