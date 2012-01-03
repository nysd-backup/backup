/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import java.util.List;

import kosmos.framework.core.query.EasyUpdate;
import kosmos.framework.core.query.impl.AbstractLimitedOrmUpdate;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;


/**
 * EasyUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultEasyUpdate<T> extends AbstractLimitedOrmUpdate<T> implements EasyUpdate<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultEasyUpdate(OrmUpdate<T> delegate) {
		super(delegate);
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#set(java.lang.String[])
	 */
	@Override
	public EasyUpdate<T> set(String... setString) {
		delegate.set(setString);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#filter(java.lang.String)
	 */
	@Override
	public EasyUpdate<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int execute(List<Object> set, Object... params) {		
		return delegate.update(set, params);
	}

}
