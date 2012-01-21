/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import kosmos.framework.core.query.Metadata;
import kosmos.framework.core.query.StrictUpdate;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;

/**
 * StrictUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultStrictUpdate<T> extends AbstractLimitedOrmUpdate<T> implements StrictUpdate<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultStrictUpdate(OrmUpdate<T> delegate) {
		super(delegate);
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#eq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictUpdate#gt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictUpdate#lt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictUpdate#gtEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#ltEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictUpdate#between(kosmos.framework.core.query.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictUpdate#set(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> set(Metadata<T,V> column , V value ){
		delegate.set(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#update()
	 */
	@Override
	public int update() {
		return delegate.update();
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#addBatch()
	 */
	@Override
	public StrictUpdate<T> addBatch() {
		delegate.addBatch();
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#batchUpdate()
	 */
	@Override
	public int[] batchUpdate() {
		return delegate.batchUpdate();
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#delete()
	 */
	@Override
	public int delete() {
		return delegate.delete();
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#eqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> StrictUpdate<T> eqFix(Metadata<T, V> column, String value) {
		delegate.eq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#gtFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> StrictUpdate<T> gtFix(Metadata<T, V> column, String value) {
		delegate.gt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#ltFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> StrictUpdate<T> ltFix(Metadata<T, V> column, String value) {
		delegate.lt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#gtEqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> StrictUpdate<T> gtEqFix(Metadata<T, V> column, String value) {
		delegate.gtEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#ltEqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> StrictUpdate<T> ltEqFix(Metadata<T, V> column, String value) {
		delegate.ltEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictUpdate#setFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> StrictUpdate<T> setFix(Metadata<T, V> column, String value) {
		delegate.set(column.name(),new FixString(value));
		return this;
	}
}

