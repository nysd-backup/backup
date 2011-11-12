/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.query.services;

import java.util.List;

import kosmos.framework.sqlclient.api.free.NativeResult;


/**
 * A native query service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NativeQueryService {

	/**
	 * Gets the count.
	 * 
	 * @param request the request
	 * @return the count
	 */
	public int count(QueryRequest request);
	
	/**
	 * Searches the records.
	 * 
	 * @param request the request
	 * @return the result
	 */
	public <T> List<T> getResultList(QueryRequest request);
	
	/**
	 * Searches the count of records hit and limited records.
	 * 
	 * @param request the request
	 * @return the result
	 */
	public <T> NativeResult<T> getTotalResult(QueryRequest request);
	
	/**
	 * Searches the first result.
	 * 
	 * @param request the result
	 * @return the result
	 */
	public <T> T getSingleResult(QueryRequest request);
	
	/**
	 * Determines whether the result is found.
	 * 
	 * @param request the request
	 * @return true:exsits
	 */
	public boolean exists(QueryRequest request);
	
	
}
