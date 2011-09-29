/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import framework.core.entity.Metadata;

/**
 * The ORM updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StrictUpdate<T> extends AdvancedOrmUpdate<T>{

	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> StrictUpdate<T> eq(Metadata<T, V> column, V value);

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> StrictUpdate<T> gt(Metadata<T, V> column, V value);

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> StrictUpdate<T> lt(Metadata<T, V> column, V value);

	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> StrictUpdate<T> gtEq(Metadata<T, V> column, V value);

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> StrictUpdate<T> ltEq(Metadata<T, V> column, V value);

	/**
	 * Adds 'between'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param from the from-value to be added
	 * @param to the to-value to be added 
	 * @return self
	 */
	public <V> StrictUpdate<T> between(Metadata<T, V> column,V from, V to);
	
	/**
	 * Adds value to update.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> StrictUpdate<T> set(Metadata<T, V> column, V value);

	
	/**
	 * Updates the table.
	 * 
	 * @return the updated count
	 */
	public int update();


}
