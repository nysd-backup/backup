package kosmos.framework.sqlclient.api.orm;

import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.QueryCallback;


/**
 * The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmQuery<T> extends Query{
	
	/**
	 * @return the current condition
	 */
	OrmQueryParameter<T> getCurrentParams();
	
	/**
	 * Adds '='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmQuery<T> eq(String column, Object value);

	/**
	 * Adds '>'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmQuery<T> gt(String column, Object value);

	/**
	 * Adds '<'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmQuery<T> lt(String column, Object value);

	/**
	 * Adds '>='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmQuery<T> gtEq(String column, Object value);

	/**
	 * Adds '<='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmQuery<T> ltEq(String column, Object value);

	/**
	 * Adds 'between'.
	 * 
	 * @param column the columnt to add to
	 * @param from the from value
	 * @param to the to value
	 * @return self
	 */
	OrmQuery<T> between(String column, Object from, Object to);
	
	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	OrmQuery<T> contains(String column, List<?> value);

	/**
	 * Adds 'asc'.
	 * 
	 * @param <V> the type
	 * @param column the column
	 * @return self
	 */
	OrmQuery<T> asc(String column);

	/**
	 * Adds 'desc'.
	 * 
	 * @param <V> the type
	 * @param column the column
	 * @return self
	 */
	OrmQuery<T> desc(String column);

	/**
	 * @param filterString the filterString
	 * @return self
	 */
	OrmQuery<T> filter(String filterString); 
	
	/**
	 * @param orderString order by 
	 * @return self　
	 */
	OrmQuery<T> order(String orderString); 
	
	/**
	 * Finds the result.
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	T single(Object... params);
	
	/**
	 * Searches the result.
	 * 
	 * @param params the parmaeters
	 * @return the result
	 */
	List<T> list(Object... params);
	
	/**
	 * Finds the result by primary keys.
	 * Throws the error if the multiple result is found.
	 * 
	 * @param poks the primary keys
	 * @return the result
	 */
	T find(Object... pks);

	/**
	 * Sets the condition.
	 * 
	 * @param condition the condition
	 */
	OrmQuery<T> setCondition(OrmQueryParameter<T> condition);
	
	/**
	 * @param type the lock mode
	 * @return self
	 */
	OrmQuery<T> setLockMode(LockModeType type);
	
	/**
	 * @param callback the callback
	 * @return the result count
	 */
	long getFetchResult(QueryCallback<T> callback);
	

	/**
	 * @param callback the callback
	 * @param params the condition
	 * @return the result count
	 */
	long fetch(QueryCallback<T> callback,Object... params);
	
}
