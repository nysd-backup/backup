/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import java.util.List;

import kosmos.framework.core.query.LightUpdate;
import kosmos.framework.core.query.impl.AbstractOrmUpdateWrapper;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;


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
	 * @see kosmos.framework.core.query.LightUpdate#set(java.lang.String[])
	 */
	@Override
	public LightUpdate<T> set(String... setString) {
		delegate.set(setString);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.LightUpdate#filter(java.lang.String)
	 */
	@Override
	public LightUpdate<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.LightUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int execute(List<Object> set, Object... params) {		
		return delegate.update(set, params);
	}

}
