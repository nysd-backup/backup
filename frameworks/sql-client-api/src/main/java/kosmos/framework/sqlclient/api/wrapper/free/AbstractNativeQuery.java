/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.free;

import java.util.List;

import kosmos.framework.sqlclient.api.free.NativeQuery;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;



/**
 * The native query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNativeQuery extends AbstractFreeQuery<NativeQuery>{

	/**
	 * Gets the total result.
	 * @return the result
	 */
	public NativeResult getTotalResult() {
		return delegate.getTotalResult();
	}
	
	/**
	 * Gets the result with fetching to cursor.
	 * @param callback the callback
	 * @return the hit count
	 */
	public long getFetchResult(QueryCallback<?> callback){
		return delegate.getFetchResult(callback);
	}
	
	/**
	 * Gets the result with fetching to cursor.
	 * @return the result holding ResultSet
	 */
	public <T> List<T> getFetchResult(){
		return delegate.getFetchResult();
	}

	/**
	 * Sets the query filter
	 * @param filter the filter
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractNativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}
	
}
