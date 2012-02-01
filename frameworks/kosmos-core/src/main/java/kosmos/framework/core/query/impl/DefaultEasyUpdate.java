/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import kosmos.framework.core.query.EasyUpdate;
import kosmos.framework.core.query.Metadata;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;

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
	 * @see kosmos.framework.core.query.EasyUpdate#eq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyUpdate#gt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyUpdate#lt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyUpdate#gtEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#ltEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyUpdate#between(kosmos.framework.core.query.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyUpdate#set(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyUpdate<T> set(Metadata<T,V> column , V value ){
		delegate.set(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#update()
	 */
	@Override
	public int update() {
		return delegate.update();
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#addBatch()
	 */
	@Override
	public EasyUpdate<T> addBatch() {
		delegate.addBatch();
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#batchUpdate()
	 */
	@Override
	public int[] batchUpdate() {
		return delegate.batchUpdate();
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#delete()
	 */
	@Override
	public int delete() {
		return delegate.delete();
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#eqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> eqFix(Metadata<T, V> column, String value) {
		delegate.eq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#gtFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> gtFix(Metadata<T, V> column, String value) {
		delegate.gt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#ltFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> ltFix(Metadata<T, V> column, String value) {
		delegate.lt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#gtEqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> gtEqFix(Metadata<T, V> column, String value) {
		delegate.gtEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#ltEqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> ltEqFix(Metadata<T, V> column, String value) {
		delegate.ltEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyUpdate#setFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyUpdate<T> setFix(Metadata<T, V> column, String value) {
		delegate.set(column.name(),new FixString(value));
		return this;
	}
}

