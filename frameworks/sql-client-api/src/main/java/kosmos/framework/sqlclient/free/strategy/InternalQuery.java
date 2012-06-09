/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free.strategy;

import java.util.List;

import kosmos.framework.sqlclient.free.FreeSelectParameter;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.free.NativeResult;

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
	int executeUpdate(FreeUpdateParameter param);
	
	/**
	 * Updates the table.
	 * 
	 * @return the each updated count
	 */
	int[] executeBatch(List<FreeUpdateParameter> param);

	/**
	 * @return the total result
	 */
	NativeResult getTotalResult(FreeSelectParameter param);

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	<T> List<T> getFetchResult(FreeSelectParameter param);

	/**
	 * @return the hit count.
	 */
	long count(FreeSelectParameter param);

	/**
	 * Selects the table.
	 * 
	 * @return the found data.
	 */
	<T> List<T> getResultList(FreeSelectParameter param);

	/**
	 * Selects the table.
	 * 
	 * @return the found one record.
	 */
	<T> T getSingleResult(FreeSelectParameter param);

}