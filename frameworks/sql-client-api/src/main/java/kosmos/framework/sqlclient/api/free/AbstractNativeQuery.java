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
public abstract class AbstractNativeQuery extends AbstractFreeQuery<NativeQuery> implements NativeQuery{

	/**
	 * @see kosmos.framework.sqlclient.api.free.AbstractFreeQuery#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		return delegate.getTotalResult();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	public <T> List<T> getFetchResult(){
		return delegate.getFetchResult();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setFilter(kosmos.framework.sqlclient.api.free.ResultSetFilter)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setQueryTimeout(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends NativeQuery> T setQueryTimeout(int seconds) {
		delegate.setQueryTimeout(seconds);
		return (T)this;
	}
}
