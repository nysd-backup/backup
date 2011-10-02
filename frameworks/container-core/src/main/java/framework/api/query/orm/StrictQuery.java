/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import java.util.List;

import framework.core.entity.Metadata;

/**
 * StrictQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StrictQuery<T> extends AdvancedOrmQuery<T>{

	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> eq(Metadata<T, V> column, V value);

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gt(Metadata<T, V> column, V value);

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> lt(Metadata<T, V> column, V value);

	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gtEq(Metadata<T, V> column, V value);

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> ltEq(Metadata<T, V> column, V value);

	/**
	 * Adds 'between'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param from the from-value to be added
	 * @param to the to-value to be added 
	 * @return self
	 */
	public abstract <V> StrictQuery<T> between(Metadata<T, V> column,
			V from, V to);

	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> containsList(Metadata<T, V> column, List<V> value);
	
	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> contains(Metadata<T, V> column, V... value);
	
	/**
	 * Adds 'ASC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public abstract StrictQuery<T> asc(Metadata<T, ?> column);

	/**
	 * Adds 'DESC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public abstract StrictQuery<T> desc(Metadata<T, ?> column);

	/**
	 * Finds by alter key.
	 * Throw the error if the multiple result is found.
	 * 
	 * @return　the result
	 */
	public abstract T findAny();

	/**
	 * Searches the result.
	 * 
	 * @return the result
	 */
	public abstract List<T> getResultList();

	/**
	 * Searches the first result.
	 * 
	 * @return the result
	 */
	public abstract T getSingleResult();

	/**
	 * Determines whether the result searched by alter keys is found.
	 * 
	 * @return true:exsists
	 */
	public abstract boolean existsByAny();	

	/**
	 * Determines whether the result searched by primary keys is found.
	 * 
	 * @return true:exsists
	 */
	public abstract boolean exists();
}
