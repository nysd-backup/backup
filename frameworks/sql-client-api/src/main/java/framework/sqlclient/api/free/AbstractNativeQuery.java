/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

import java.util.List;


/**
 * ネイティブクエリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractNativeQuery extends AbstractFreeQuery<NativeQuery> implements NativeQuery{

	/**
	 * @see framework.sqlclient.api.free.AbstractFreeQuery#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		return delegate.getTotalResult();
	}
	
	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	public <T> List<T> getFetchResult(){
		return delegate.getFetchResult();
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#setFilter(framework.sqlclient.api.free.ResultSetFilter)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}
}
