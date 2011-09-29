/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

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
	public <T> NativeResult<T> getTotalResult();
	
	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	public <T> List<T> getFetchResult();
	
	/**
	 * @param <T> the type
	 * @param filter the filter for <code>ResultSet</code>
	 * @return self
	 */
	@SuppressWarnings("rawtypes")
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter);
	
}
