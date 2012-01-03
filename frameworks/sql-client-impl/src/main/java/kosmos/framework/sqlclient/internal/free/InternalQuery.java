/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free;

import java.util.List;

import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;

/**
 * The internal query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface InternalQuery {
	
	/**
	 * Updates the table.
	 * 
	 * @return the updated count
	 */
	int executeUpdate(FreeUpdateParameter param);
	
	/**
	 * Executes the batch.
	 * 
	 * @return result
	 */
	int[] batchUpdate(FreeUpdateParameter param);

	/**
	 * @return the total result
	 */
	NativeResult getTotalResult(FreeQueryParameter param);

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	<T> List<T> getFetchResult(FreeQueryParameter param);

	/**
	 * @return the hit count.
	 */
	int count(FreeQueryParameter param);

	/**
	 * Selects the table.
	 * 
	 * @return the found data.
	 */
	<T> List<T> getResultList(FreeQueryParameter param);

	/**
	 * Selects the table.
	 * 
	 * @return the found one record.
	 */
	<T> T getSingleResult(FreeQueryParameter param);

}