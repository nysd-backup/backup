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
	 * @param queries the sql command
	 */
	public List<List<Object>> getChainedResultLists(QueryRequest... request);
	
	/**
	 * @param queries the sql command
	 */
	public List<List<Object>> getResultLists(Serializable... request);
		
}
