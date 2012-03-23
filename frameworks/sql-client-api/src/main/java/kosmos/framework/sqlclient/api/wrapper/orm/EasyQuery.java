/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm;

import java.util.List;

import kosmos.framework.sqlclient.api.free.QueryCallback;



/**
 * EasyQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EasyQuery<T> extends OrmQueryWrapper<T>{

	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> eq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> eqFix(Metadata<T, V> column, String value);

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> gt(Metadata<T, V> column, V value);
	
	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> gtFix(Metadata<T, V> column, String value);

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> lt(Metadata<T, V> column, V value);
	
	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> ltFix(Metadata<T, V> column, String value);

	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> gtEq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> gtEqFix(Metadata<T, V> column, String value);

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> ltEq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> ltEqFix(Metadata<T, V> column, String value);

	/**
	 * Adds 'between'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param from the from-value to be added
	 * @param to the to-value to be added 
	 * @return self
	 */
	<V> EasyQuery<T> between(Metadata<T, V> column,
			V from, V to);

	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> containsList(Metadata<T, V> column, List<V> value);
	
	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyQuery<T> contains(Metadata<T, V> column, V... value);
	
	/**
	 * Adds 'ASC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	EasyQuery<T> asc(Metadata<T, ?> column);

	/**
	 * Adds 'DESC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	EasyQuery<T> desc(Metadata<T, ?> column);

	/**
	 * Searches the result.
	 * 
	 * @return the result
	 */
	List<T> getResultList();

	/**
	 * Searches the first result.
	 * 
	 * @return the result
	 */
	T getSingleResult();

	/**
	 * Determines whether the result searched by primary keys is found.
	 * 
	 * @return true:exsists
	 */
	boolean exists();
	
	/**
	 * Executes the callback after select.
	 * @param callback the callback to execute
	 * @return the result count
	 */
	long getFetchResult(QueryCallback<T> callback);
	
	
}
