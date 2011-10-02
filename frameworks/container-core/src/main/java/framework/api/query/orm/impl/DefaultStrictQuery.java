/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm.impl;

import java.util.Arrays;
import java.util.List;

import framework.api.query.orm.StrictQuery;
import framework.core.entity.Metadata;
import framework.sqlclient.api.orm.OrmQuery;

/**
 * StrictQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultStrictQuery<T> extends AbstractAdvancedOrmQuery<T> implements StrictQuery<T>{

	/**
	 * @param delegate the delegate to set
	 */
	public DefaultStrictQuery(OrmQuery<T> delegate){
		super(delegate);
	}

	/**
	 * @see framework.api.query.orm.StrictQuery#eq(framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#gt(framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#lt(framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#gtEq(framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#ltEq(framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#between(framework.core.entity.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#containsList(framework.core.entity.Metadata, java.util.List)
	 */
	@Override
	public <V> StrictQuery<T> containsList(Metadata<T, V> column, List<V> value) {
		delegate.contains(column.name(), value);
		return this;
	}

	/**
	 * @see framework.api.query.orm.StrictQuery#contains(framework.core.entity.Metadata, V[])
	 */
	@Override
	public <V> StrictQuery<T> contains(Metadata<T, V> column, V... value) {
		return containsList(column,Arrays.asList(value));
	}

	/**
	 * @see framework.api.query.orm.StrictQuery#asc(framework.core.entity.Metadata)
	 */
	@Override
	public StrictQuery<T> asc(Metadata<T,?> column){
		delegate.asc(column.name());
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#desc(framework.core.entity.Metadata)
	 */
	@Override
	public StrictQuery<T> desc(Metadata<T,?> column){
		delegate.desc(column.name());
		return this;
	}	

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public T findAny(){
		return delegate.findAny();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#getResultList()
	 */
	@Override
	public List<T> getResultList(){
		return delegate.getResultList();
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#getSingleResult()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getSingleResult(){
		return (T)delegate.getSingleResult();
	}	
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny(){
		return delegate.existsByAny();
	}
	
	/**
	 * @see framework.api.query.orm.StrictQuery#exists()
	 */
	@Override
	public boolean exists() {
		return delegate.exists();
	}

}

