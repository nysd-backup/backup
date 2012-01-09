package kosmos.framework.sqlclient.api.orm;

import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.Query;


/**
 * The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmQuery<T> extends Query{
	
	/**
	 * @return
	 */
	public OrmQueryParameter<T> getCurrentParams();
	
	/**
	 * Adds '='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public OrmQuery<T> eq(String column, Object value);

	/**
	 * Adds '>'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public OrmQuery<T> gt(String column, Object value);

	/**
	 * Adds '<'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public OrmQuery<T> lt(String column, Object value);

	/**
	 * Adds '>='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public OrmQuery<T> gtEq(String column, Object value);

	/**
	 * Adds '<='.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public OrmQuery<T> ltEq(String column, Object value);

	/**
	 * Adds 'between'.
	 * 
	 * @param column the columnt to add to
	 * @param from the from value
	 * @param to the to value
	 * @return self
	 */
	public OrmQuery<T> between(String column, Object from, Object to);
	
	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public OrmQuery<T> contains(String column, List<?> value);

	/**
	 * Adds 'asc'.
	 * 
	 * @param <V> the type
	 * @param column the column
	 * @return self
	 */
	public OrmQuery<T> asc(String column);

	/**
	 * Adds 'desc'.
	 * 
	 * @param <V> the type
	 * @param column the column
	 * @return self
	 */
	public OrmQuery<T> desc(String column);

	/**
	 * @param filterString the filterString
	 * @return self
	 */
	public OrmQuery<T> filter(String filterString); 
	
	/**
	 * @param orderString order by 
	 * @return self　
	 */
	public OrmQuery<T> order(String orderString); 
	
	/**
	 * Finds the result.
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	public T single(Object... params);
	
	/**
	 * Searches the result.
	 * 
	 * @param params the parmaeters
	 * @return the result
	 */
	public List<T> list(Object... params);
	
	/**
	 * Finds the result by primary keys.
	 * Throws the error if the multiple result is found.
	 * 
	 * @param poks the primary keys
	 * @return the result
	 */
	public T find(Object... pks);
	
	/**
	 * Finds by primary key for update.
	 * @param pks　the primary keys
	 * @return the result
	 */
	public T findForUpdate(Object... pks);

	/**
	 * Sets the condition.
	 * 
	 * @param condition the condition
	 */
	public OrmQuery<T> setCondition(OrmQueryParameter<T> condition);
	
	/**
	 * @param type the lock mode
	 * @return self
	 */
	public OrmQuery<T> setLockMode(LockModeType type);
	
}
