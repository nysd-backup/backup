/**
 * Copyright 2011 the original author
 */
package framework.api.query.services;


/**
 * The paging service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface PagingService {

	/**
	 * Prepares the paging data.
	 * Only can be called in first search. 
	 * 
	 * @param request　the request
	 * @return the result
	 */
	public PagingResult prepare(PagingRequest request);
	
	/**
	 * Gets the paging data.
	 * 
	 * @param request the request
	 * @return the result
	 */
	public PagingResult getPageData(PagingRequest request);

}
