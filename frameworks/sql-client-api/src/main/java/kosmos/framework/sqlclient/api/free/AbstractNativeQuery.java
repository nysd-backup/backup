/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;



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
	public long count() {
		return delegate.count();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public NativeResult getTotalResult() {
		return delegate.getTotalResult();
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	public long getFetchResult(QueryCallback<?> callback){
		return delegate.getFetchResult(callback);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setFilter(kosmos.framework.sqlclient.api.free.ResultSetFilter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}
	
}
