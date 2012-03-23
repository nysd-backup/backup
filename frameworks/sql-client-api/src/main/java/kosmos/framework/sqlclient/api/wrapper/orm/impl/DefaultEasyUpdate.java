/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm.impl;

import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.Metadata;

/**
 * EasyUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultEasyUpdate<T> extends AbstractOrmUpdateWrapper<T> implements EasyUpdate<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultEasyUpdate(OrmUpdate<T> delegate) {
		super(delegate);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#eq(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#gt(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#lt(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#gtEq(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#ltEq(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#between(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#set(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> set(Metadata<T,V> column , V value ){
		delegate.set(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#update()
	 */
	@Override
	public int update() {
		return delegate.update();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#delete()
	 */
	@Override
	public int delete() {
		return delegate.delete();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#eqFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> eqFix(Metadata<T, V> column, String value) {
		delegate.eq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#gtFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> gtFix(Metadata<T, V> column, String value) {
		delegate.gt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#ltFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> ltFix(Metadata<T, V> column, String value) {
		delegate.lt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#gtEqFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> gtEqFix(Metadata<T, V> column, String value) {
		delegate.gtEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#ltEqFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> ltEqFix(Metadata<T, V> column, String value) {
		delegate.ltEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate#setFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> setFix(Metadata<T, V> column, String value) {
		delegate.set(column.name(),new FixString(value));
		return this;
	}
}

