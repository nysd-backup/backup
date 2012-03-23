/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm.impl;

import java.util.Arrays;
import java.util.List;

import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery;
import kosmos.framework.sqlclient.api.wrapper.orm.Metadata;


/**
 * EasyQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultEasyQuery<T> extends AbstractOrmQueryWrapper<T> implements EasyQuery<T>{

	/**
	 * @param delegate the delegate to set
	 */
	public DefaultEasyQuery(OrmQuery<T> delegate){
		super(delegate);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#eq(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#gt(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#lt(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#gtEq(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#ltEq(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#between(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#containsList(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.util.List)
	 */
	@Override
	public <V> EasyQuery<T> containsList(Metadata<T, V> column, List<V> value) {
		delegate.contains(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#contains(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, V[])
	 */
	@Override
	public <V> EasyQuery<T> contains(Metadata<T, V> column, V... value) {
		return containsList(column,Arrays.asList(value));
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#asc(kosmos.framework.sqlclient.api.wrapper.orm.Metadata)
	 */
	@Override
	public EasyQuery<T> asc(Metadata<T,?> column){
		delegate.asc(column.name());
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#desc(kosmos.framework.sqlclient.api.wrapper.orm.Metadata)
	 */
	@Override
	public EasyQuery<T> desc(Metadata<T,?> column){
		delegate.desc(column.name());
		return this;
	}	

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#getResultList()
	 */
	@Override
	public List<T> getResultList(){
		return delegate.getResultList();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#getSingleResult()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getSingleResult(){
		return (T)delegate.getSingleResult();
	}	
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#exists()
	 */
	@Override
	public boolean exists() {
		return getSingleResult() != null;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapper#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return find(pks) != null;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#eqFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> eqFix(Metadata<T, V> column, String value) {
		delegate.eq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#gtFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> gtFix(Metadata<T, V> column, String value) {
		delegate.gt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#ltFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> ltFix(Metadata<T, V> column, String value) {
		delegate.lt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#gtEqFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> gtEqFix(Metadata<T, V> column, String value) {
		delegate.gtEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#ltEqFix(kosmos.framework.sqlclient.api.wrapper.orm.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> ltEqFix(Metadata<T, V> column, String value) {
		delegate.ltEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery#getFetchResult(kosmos.framework.sqlclient.api.free.QueryCallback)
	 */
	@Override
	public long getFetchResult(QueryCallback<T> callback) {
		return delegate.getFetchResult(callback);
	}

}

