/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import java.util.List;


/**
 * EasyQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EasyQuery<T> extends AdvancedOrmQuery<T>{

	/**
	 * Adds the string of the filter to search.
	 * 
	 * @return self
	 */
	public EasyQuery<T> filter(String filterString);
	
	/**
	 * Adds the string to sort.
	 * 
	 * @return self
	 */
	public EasyQuery<T> order(String orderString);
	
	/**
	 * Searches the list of entities.
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	public List<T> list(Object... params);
	
	/**
	 * Finds by alter key.
	 * Throw the error if the multiple result is found.
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	public T findAny(Object... params);
	
	/**
	 * Searches first record hit.
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	public T single(Object... params);
	
	/**
	 * Determines whether the result searched by alter keys is found.
	 * Throw error if the multiple result is found.  
	 * 
	 * @param params the parameters
	 * @return the result
	 */
	public boolean existsByAny(Object... params);

	/**
	 * Determines whether the result is found.
	 * 
	 * @param params the parmaeter
	 * @return the result
	 */
	public boolean existsByList(Object... params);

}
