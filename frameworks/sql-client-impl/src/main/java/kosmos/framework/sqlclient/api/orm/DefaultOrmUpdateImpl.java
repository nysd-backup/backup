/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

import java.util.List;


/**
 * The default ORM query.
 * 
 * <pre>
 * Don't allow to use this.
 * Create the wrapper class for this.
 * the methods like eq and asc should be type safe.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultOrmUpdateImpl<T> implements OrmUpdate<T>{
	
	/** the delegate */
	private OrmUpdate<T> delegate;

	/**
	 * @param delegate the delegate
	 */
	public DefaultOrmUpdateImpl(OrmUpdate<T> delegate){
		this.delegate = delegate;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> eq(String column, Object value) {
		delegate.eq(column, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gt(String column, Object value) {
		delegate.gt(column, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> lt(String column, Object value) {
		delegate.lt(column, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gtEq(String column, Object value) {
		delegate.gtEq(column, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> ltEq(String column, Object value) {
		delegate.ltEq(column, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> between(String column, Object from, Object to) {
		delegate.between(column, from,to);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmUpdate<T> contains(String column, List<?> value) {
		delegate.contains(column, value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> set(String column, Object value) {
		delegate.set(column, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return delegate.update();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	public OrmUpdate<T> setCondition(OrmUpdateParameter<T> condition) {
		delegate.setCondition(condition);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String[])
	 */
	@Override
	public OrmUpdate<T> set(String... setString) {
		delegate.set(setString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#filter(java.lang.String)
	 */
	@Override
	public OrmUpdate<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int update(List<Object> set, Object... params) {
		return delegate.update(set, params);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrmUpdate<T> setHint(String key, Object value) {
		delegate.setHint(key, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#addBatch()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrmUpdate<T> addBatch() {
		delegate.addBatch();
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#batchUpdate()
	 */
	@Override
	public int[] batchUpdate() {
		return delegate.batchUpdate();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#delete()
	 */
	@Override
	public int delete() {
		return delegate.delete();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#getCurrentParams()
	 */
	@Override
	public OrmUpdateParameter<T> getCurrentParams() {
		return delegate.getCurrentParams();
	}



}
