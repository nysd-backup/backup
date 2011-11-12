/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.List;

/**
 * The base of the queries.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Query {

	/**
	 * Marks that exception is thrown if no data is found.
	 * 
	 * @param <T> the type
	 * @return self
	 */
	public <T extends Query> T enableNoDataError();
	
	/**
	 * @param <T> the type
	 * @param arg0 the max result
	 * @return self
	 */
	public <T extends Query> T setMaxResults(int arg0) ;
	
	/**
	 * @param <T>　the type
	 * @param arg0　the start position
	 * @return self
	 */
	public <T extends Query> T setFirstResult(int arg0) ;
	
	/**
	 * @param <T> the type
	 * @return the result
	 */
	public <T> List<T> getResultList() ;

	/**
	 * @param <T> the type
	 * @return the first result hit
	 */
	public <T> T getSingleResult();
	
	/**
	 * @return the count
	 */
	public int count();
	
	/**
	 * Determines whether the result is found.
	 * 
	 * @return true:exists
	 */
	public boolean exists();

}
