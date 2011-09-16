/**
 * Use is subject to license terms.
 */
package framework.api.query.orm.impl;

import java.util.List;

import framework.api.query.orm.AbstractAdvancedOrmUpdate;
import framework.api.query.orm.EasyUpdate;
import framework.sqlclient.api.orm.OrmUpdate;

/**
 * DefaultEasyUpdate.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultEasyUpdate<T> extends AbstractAdvancedOrmUpdate<T> implements EasyUpdate<T>{

	/**
	 * @param delegate
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
