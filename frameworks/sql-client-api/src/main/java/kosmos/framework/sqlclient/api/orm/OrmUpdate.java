package kosmos.framework.sqlclient.api.orm;

import java.util.List;

import kosmos.framework.sqlclient.api.Update;


/**
 * The ORM Updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmUpdate<T> extends Update{
	
	/**
	 * @return the parameter
	 */
	OrmUpdateParameter<T> getCurrentParams();
	
	/**
	 * Adds '='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmUpdate<T> eq(String column, Object value);

	/**
	 * Adds '>'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmUpdate<T> gt(String column, Object value);

	/**
	 * Adds '<'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmUpdate<T> lt(String column, Object value);

	/**
	 * Adds '>='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmUpdate<T> gtEq(String column, Object value);

	/**
	 * Adds '<='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmUpdate<T> ltEq(String column, Object value);

	/**
	 * Adds 'between'.
	 * 
	 * @param column the columnt to add to
	 * @param from the from value
	 * @param to the to value
	 * @return self
	 */
	OrmUpdate<T> between(String column, Object from, Object to);
	
	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmUpdate<T> contains(String column, List<?> value);
	
	/**
	 * Adds the updating value.
	 * 
	 * @param column the column to update to.
	 * @param value the value 
	 * @return self
	 */
	OrmUpdate<T> set(String column , Object value);
	
	/**
	 * Adds the statement of update.
	 * 
	 * @param setString the set
	 * @return self 
	 */
	OrmUpdate<T> set(String... setString);
	
	/**
	 * Add the filter to update.
	 * 
	 * @param filterString the filter
	 * @return self
	 */
	OrmUpdate<T> filter(String filterString);
	
	/**
	 * Updates the data.
	 * 
	 * @param set the set
	 * @param params the parameters
	 * @return the updated count
	 */
	int update(List<Object> set , Object... params);
	
	/**
	 * Sets the condition.
	 * 
	 * @param condition the condition
	 */
	OrmUpdate<T> setCondition(OrmUpdateParameter<T> condition);
	
	/**
	 * Deletes the data.
	 * 
	 * @return the updated count
	 */
	int delete();

}
