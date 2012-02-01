/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import java.util.Arrays;
import java.util.List;

import kosmos.framework.core.query.Metadata;
import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmQuery;


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
	 * @see kosmos.framework.core.query.EasyQuery#eq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyQuery#gt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyQuery#lt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyQuery#gtEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyQuery#ltEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyQuery#between(kosmos.framework.core.query.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> EasyQuery<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyQuery#containsList(kosmos.framework.core.query.Metadata, java.util.List)
	 */
	@Override
	public <V> EasyQuery<T> containsList(Metadata<T, V> column, List<V> value) {
		delegate.contains(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#contains(kosmos.framework.core.query.Metadata, V[])
	 */
	@Override
	public <V> EasyQuery<T> contains(Metadata<T, V> column, V... value) {
		return containsList(column,Arrays.asList(value));
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#asc(kosmos.framework.core.query.Metadata)
	 */
	@Override
	public EasyQuery<T> asc(Metadata<T,?> column){
		delegate.asc(column.name());
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.EasyQuery#desc(kosmos.framework.core.query.Metadata)
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
	 * @see kosmos.framework.core.query.EasyQuery#exists()
	 */
	@Override
	public boolean exists() {
		return getSingleResult() != null;
	}

	/**
	 * @see kosmos.framework.core.query.OrmQueryWrapper#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return find(pks) != null;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#eqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> eqFix(Metadata<T, V> column, String value) {
		delegate.eq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#gtFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> gtFix(Metadata<T, V> column, String value) {
		delegate.gt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#ltFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> ltFix(Metadata<T, V> column, String value) {
		delegate.lt(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#gtEqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> gtEqFix(Metadata<T, V> column, String value) {
		delegate.gtEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#ltEqFix(kosmos.framework.core.query.Metadata, java.lang.String)
	 */
	@Override
	public <V> EasyQuery<T> ltEqFix(Metadata<T, V> column, String value) {
		delegate.ltEq(column.name(), new FixString(value));
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.EasyQuery#getFetchResult(kosmos.framework.sqlclient.api.free.QueryCallback)
	 */
	@Override
	public long getFetchResult(QueryCallback<T> callback) {
		return delegate.getFetchResult(callback);
	}

}

