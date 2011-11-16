/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import java.util.List;

import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.core.query.impl.AbstractAdvancedOrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQuery;


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
	 * @see kosmos.framework.core.query.EasyQuery#filter(java.lang.String)
	 */
	@Override
	public EasyQuery<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#order(java.lang.String)
	 */
	@Override
	public EasyQuery<T> order(String orderString) {
		delegate.order(orderString);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params) {
		return delegate.list(params);
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params) {
		return delegate.single(params);
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#findAny(java.lang.Object[])
	 */
	@Override
	public T findAny(Object... params) {
		return delegate.findAny(params);
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#existsByAny(java.lang.Object[])
	 */
	@Override
	public boolean existsByAny(Object... params) {
		return findAny(params) != null;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#existsByList(java.lang.Object[])
	 */
	@Override
	public boolean existsByList(Object... params) {
		return !delegate.list(params).isEmpty();
	}	

}
