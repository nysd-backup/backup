/**
 * Copyright 2011 the original author
 */
package client.sql.free.strategy;

import java.util.List;

import client.sql.free.FreeReadQueryParameter;
import client.sql.free.FreeModifyQueryParameter;
import client.sql.free.HitData;



/**
 * The internal query.
 * 
 * <pre>
 * Native and JPA is supported. 
 * </pre>
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
	int executeUpdate(FreeModifyQueryParameter param);
	
	/**
	 * Updates the table.
	 * 
	 * @return the each updated count
	 */
	int[] executeBatch(List<FreeModifyQueryParameter> param);

	/**
	 * @return the total result
	 */
	HitData getTotalResult(FreeReadQueryParameter param);

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	<T> List<T> getFetchResult(FreeReadQueryParameter param);

	/**
	 * @return the hit count.
	 */
	long count(FreeReadQueryParameter param);

	/**
	 * Selects the table.
	 * 
	 * @return the found data.
	 */
	<T> List<T> getResultList(FreeReadQueryParameter param);

	/**
	 * Selects the table.
	 * 
	 * @return the found one record.
	 */
	<T> T getSingleResult(FreeReadQueryParameter param);

}