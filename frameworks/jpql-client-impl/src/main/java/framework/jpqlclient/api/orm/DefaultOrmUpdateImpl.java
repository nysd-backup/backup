/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.orm;

import java.util.List;

import framework.sqlclient.api.orm.OrmUpdate;

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
public class DefaultOrmUpdateImpl<T> implements JPAOrmUpdate<T>{
	
	/** the delegate */
	private JPAOrmUpdate<T> delegate;

	/**
	 * @param delegate the delegate
	 */
	public DefaultOrmUpdateImpl(JPAOrmUpdate<T> delegate){
		this.delegate = delegate;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> eq(String column, Object value) {
		delegate.eq(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gt(String column, Object value) {
		delegate.gt(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> lt(String column, Object value) {
		delegate.lt(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gtEq(String column, Object value) {
		delegate.gtEq(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> ltEq(String column, Object value) {
		delegate.ltEq(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> between(String column, Object from, Object to) {
		delegate.between(column, from,to);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmUpdate<T> contains(String column, List<?> value) {
		delegate.contains(column, value);
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> set(String column, Object value) {
		delegate.set(column, value);
		return this;
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public JPAOrmUpdate<T> setHint(String arg0, Object arg1) {
		delegate.setHint(arg0, arg1);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return delegate.update();
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmUpdate#setCondition(framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public JPAOrmUpdate<T> setCondition(JPAOrmCondition<T> condition) {
		delegate.setCondition(condition);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String[])
	 */
	@Override
	public OrmUpdate<T> set(String... setString) {
		delegate.set(setString);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#filter(java.lang.String)
	 */
	@Override
	public OrmUpdate<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int execute(List<Object> set, Object... params) {
		return delegate.execute(set, params);
	}



}
