/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query.impl;

import java.util.Arrays;
import java.util.List;

import kosmos.framework.core.query.Metadata;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.sqlclient.api.orm.OrmQuery;


/**
 * StrictQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultStrictQuery<T> extends AbstractLimitedOrmQuery<T> implements StrictQuery<T>{

	/**
	 * @param delegate the delegate to set
	 */
	public DefaultStrictQuery(OrmQuery<T> delegate){
		super(delegate);
	}

	/**
	 * @see kosmos.framework.core.query.StrictQuery#eq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictQuery#gt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictQuery#lt(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictQuery#gtEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictQuery#ltEq(kosmos.framework.core.query.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictQuery#between(kosmos.framework.core.query.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> StrictQuery<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictQuery#containsList(kosmos.framework.core.query.Metadata, java.util.List)
	 */
	@Override
	public <V> StrictQuery<T> containsList(Metadata<T, V> column, List<V> value) {
		delegate.contains(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.core.query.StrictQuery#contains(kosmos.framework.core.query.Metadata, V[])
	 */
	@Override
	public <V> StrictQuery<T> contains(Metadata<T, V> column, V... value) {
		return containsList(column,Arrays.asList(value));
	}

	/**
	 * @see kosmos.framework.core.query.StrictQuery#asc(kosmos.framework.core.query.Metadata)
	 */
	@Override
	public StrictQuery<T> asc(Metadata<T,?> column){
		delegate.asc(column.name());
		return this;
	}
	
	/**
	 * @see kosmos.framework.core.query.StrictQuery#desc(kosmos.framework.core.query.Metadata)
	 */
	@Override
	public StrictQuery<T> desc(Metadata<T,?> column){
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
	 * @see kosmos.framework.core.query.StrictQuery#exists()
	 */
	@Override
	public boolean exists() {
		return getSingleResult() != null;
	}

	/**
	 * @see kosmos.framework.core.query.LimitedOrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return find(pks) != null;
	}

}

