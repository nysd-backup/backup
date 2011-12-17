/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.services;

import java.io.Serializable;
import java.util.List;


/**
 * A consecutive SQL service.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ConsecutiveQueryService {

	/**
	 * Gets the chained result.
	 * 
	 * the condition includes the result of previous query.
	 * 
	 * @param queries the SQL
	 */
	public List<List<Object>> getChainedResultLists(QueryRequest... request);
	
	/**
	 * Gets the result lists.
	 * 
	 * @param queries the SQL/ORM query
	 */
	public List<List<Object>> getResultLists(Serializable... request);
		
}
