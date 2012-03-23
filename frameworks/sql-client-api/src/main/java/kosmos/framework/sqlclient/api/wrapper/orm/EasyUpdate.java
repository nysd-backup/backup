/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm;

/**
 * The ORM updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EasyUpdate<T> extends OrmUpdateWrapper<T>{

	/**
	 * Deletes the table.
	 * @return reesult
	 */
	int delete();
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> eq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> eqFix(Metadata<T, V> column, String value);

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> gt(Metadata<T, V> column, V value);
	
	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> gtFix(Metadata<T, V> column, String value);

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> lt(Metadata<T, V> column, V value);

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> ltFix(Metadata<T, V> column, String value);
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> gtEq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> gtEqFix(Metadata<T, V> column, String value);

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> ltEq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> ltEqFix(Metadata<T, V> column, String value);

	/**
	 * Adds 'between'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param from the from-value to be added
	 * @param to the to-value to be added 
	 * @return self
	 */
	<V> EasyUpdate<T> between(Metadata<T, V> column,V from, V to);
	
	/**
	 * Adds value to update.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> set(Metadata<T, V> column, V value);
	
	/**
	 * Adds value to update.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	<V> EasyUpdate<T> setFix(Metadata<T, V> column, String value);

	/**
	 * Updates the table.
	 * 
	 * @return the updated count
	 */
	int update();


}
