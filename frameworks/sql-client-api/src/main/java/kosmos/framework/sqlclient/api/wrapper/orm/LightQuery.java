/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm;

import java.util.List;

import kosmos.framework.sqlclient.api.free.QueryCallback;


/**
 * LightQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface LightQuery<T> extends OrmQueryWrapper<T>{

	/**
	 * Adds the string of the filter to search.
	 * 
	 * @return self
	 */
	LightQuery<T> filter(String filterString);
	
	/**
	 * Adds the string to sort.
	 * 
	 * @return self
	 */
	LightQuery<T> order(String orderString);
	
	/**
	 * Searches the list of entities.
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	List<T> list(Object... params);
	
	/**
	 * Searches first record hit.
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	T single(Object... params);
	
	/**
	 * Executes the callback after select.
	 * @param callback the callback to execute
	 * @param params the parameters
	 * @return the result count
	 */
	long fetch(QueryCallback<T> callback,Object... params);

}
