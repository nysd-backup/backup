/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.services;

import java.util.List;

import kosmos.framework.sqlclient.api.orm.OrmQueryContext;


/**
 * The ORM query service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmQueryService<T> {

	/**
	 * Finds by primary keys.
	 * 
	 * @param request the request
	 * @param pks　the primary keys
	 * @return the result
	 */
	public T find(OrmQueryContext<T> request,Object[] pks);

	/**
	 * Searches the records.
	 * 
	 * @param request the request
	 * @return the result
	 */
	public List<T> getResultList(OrmQueryContext<T> request);
	
	/**
	 * Searches the first record.
	 *
 	 * @param request the request
	 * @return the result
	 */
	public T getSingleResult(OrmQueryContext<T> request);
	
	/**
	 * Determines whether the result is found.
	 * 
	 * @param request the request
	 * @return true:exists
	 */
	public boolean exists(OrmQueryContext<T> request);
	
	/**
	 * Determines whether the result searched by primary keys is found.
	 *
	 * @param request the request
	 * @param pks the primary keys
	 * @return true:exsists
	 */
	public boolean exists(OrmQueryContext<T> request,Object[] pks);

}
