/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.api.orm;

import java.util.List;

import javax.jdo.Extent;

import framework.sqlclient.api.Query;
import framework.sqlclient.api.orm.OrmQuery;

/**
 * The ORM query for JDO.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class DefaultOrmQueryImpl<T> implements JDOOrmQuery<T>{
	
	/** the delegate */
	private JDOOrmQuery<T> delegate;

	/**
	 * @param delegate the delegate
	 */
	public DefaultOrmQueryImpl(JDOOrmQuery<T> delegate){
		this.delegate = delegate;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public JDOOrmQuery<T> eq(String column, Object value) {
		delegate.eq(column,value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public JDOOrmQuery<T> gt(String column, Object value) {
		delegate.gt(column,value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public JDOOrmQuery<T> lt(String column, Object value) {
		delegate.lt(column,value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public JDOOrmQuery<T> gtEq(String column, Object value) {
		delegate.gtEq(column,value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public JDOOrmQuery<T> ltEq(String column, Object value) {
		delegate.ltEq(column,value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> between(String column, Object from, Object to) {
		delegate.between(column,from,to);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public JDOOrmQuery<T> contains(String column, List<?> value) {
		delegate.contains(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> asc(String column) {
		delegate.asc(column);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> desc(String column) {
		delegate.desc(column);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks) {
		return delegate.find(pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public T findAny() {
		return delegate.findAny();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return delegate.exists(pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny() {
		return delegate.existsByAny();
	}
	
	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <Q extends Query> Q enableNoDataError() {
		delegate.enableNoDataError();
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <Q extends Query> Q setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <Q extends Query> Q setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<T> getResultList() {
		return delegate.getResultList();
	}

	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public T getSingleResult() {
		return (T)delegate.getSingleResult();
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return delegate.exists();
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#and()
	 */
	@Override
	public JDOOrmQuery<T> and() {
		return delegate.and();
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#or()
	 */
	@Override
	public JDOOrmQuery<T> or() {
		return delegate.or();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#filter(java.lang.String)
	 */
	@Override
	public OrmQuery<T> filter(String filterString) {
		return delegate.filter(filterString);
	}
	
	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#order(java.lang.String)
	 */
	@Override
	public OrmQuery<T> order(String orderString) {
		return delegate.order(orderString);
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#getExtent()
	 */
	@Override
	public Extent<T> getExtent() {
		return delegate.getExtent();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params){
		return delegate.single(params);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params){
		return delegate.list(params);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny(java.lang.Object[])
	 */
	@Override
	public T findAny(Object... params) {
		return delegate.findAny(params);
	}


}
