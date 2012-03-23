/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import java.util.List;



/**
 * The native query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NativeQuery extends FreeQuery{
	
	/**
	 * @return the hit count and the limited records.
	 */
	NativeResult getTotalResult();
	
	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	long getFetchResult(QueryCallback<?> callback);
	
	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	<T> List<T> getFetchResult();

	/**
	 * @param <T> the type
	 * @param filter the filter for <code>ResultSet</code>
	 * @return self
	 */
	NativeQuery setFilter(ResultSetFilter filter);
	
}
