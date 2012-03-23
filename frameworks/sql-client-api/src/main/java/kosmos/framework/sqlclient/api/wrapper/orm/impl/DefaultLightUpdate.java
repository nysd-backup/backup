/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm.impl;

import java.util.List;

import kosmos.framework.sqlclient.api.orm.OrmUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.LightUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.impl.AbstractOrmUpdateWrapper;


/**
 * LightUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultLightUpdate<T> extends AbstractOrmUpdateWrapper<T> implements LightUpdate<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultLightUpdate(OrmUpdate<T> delegate) {
		super(delegate);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightUpdate#set(java.lang.String[])
	 */
	@Override
	public LightUpdate<T> set(String... setString) {
		delegate.set(setString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightUpdate#filter(java.lang.String)
	 */
	@Override
	public LightUpdate<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.LightUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int execute(List<Object> set, Object... params) {		
		return delegate.update(set, params);
	}

}
