/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free;

import java.util.List;
import java.util.Map;

import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;

/**
 * The internal query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface InternalQuery {

	/**
	 * Set the filter.
	 * 
	 * @param filter the filter to set
	 * @return self
	 */ 
	void setFilter(ResultSetFilter filter);

	/**
	 * @return the total result
	 */
	NativeResult getTotalResult();

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	<T> List<T> getFetchResult();

	/**
	 * @return the hit count.
	 */
	int count();

	/**
	 * Selects the table.
	 * 
	 * @return the found data.
	 */
	<T> List<T> getResultList();

	/**
	 * Selects the table.
	 * 
	 * @return the found one record.
	 */
	<T> T getSingleResult();

	/**
	 * Updates the table.
	 * 
	 * @return the updated count
	 */
	int executeUpdate();

	/**
	 * @param arg0 the key 
	 * @param arg1 the value
	 */
	void setBranchParameter(String arg0 , Object arg1);

	/**
	 * @return the firstResult
	 */
	int getFirstResult() ;

	/**
	 * @return the maxResults
	 */
	int getMaxResults(); 

	/**
	 * @param arg0 the start position
	 * @return self
	 */
	void setFirstResult(int arg0) ;

	/**
	 * @param arg0 the max results
	 * @return self
	 */
	void setMaxResults(int arg0);

	/**
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	void setParameter(String arg0, Object arg1) ;
	
	/**
	 * @return the hints
	 */
	Map<String,Object> getHints();

	/**
	 * @param hints the hints to set
	 */
	void setHint(String key , Object value) ;


}