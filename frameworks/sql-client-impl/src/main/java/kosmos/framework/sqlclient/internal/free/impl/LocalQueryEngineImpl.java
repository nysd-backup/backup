/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free.impl;

import java.util.List;

import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.NativeQuery;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;
import kosmos.framework.sqlclient.internal.free.AbstractLocalQueryEngine;
import kosmos.framework.sqlclient.internal.free.InternalQuery;


/**
 *　The query engine.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalQueryEngineImpl extends AbstractLocalQueryEngine<InternalQuery> implements NativeQuery{

	/**
	 * @param delegate the query to delegate
	 * @param emtpyHandler the emtpyHandler to set
	 */
	public LocalQueryEngineImpl(InternalQuery delegate){
		super(delegate);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		List<T> list = delegate.getResultList();		
		return list;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractLocalQueryEngine#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setFilter(kosmos.framework.sqlclient.api.free.ResultSetFilter)
	 */
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public NativeResult getTotalResult() {
		return (NativeResult)delegate.getTotalResult();		
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		return delegate.getFetchResult();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends Query> T setHint(String key, Object value) {
		delegate.setHint(key, value);
		return (T)this;
	}

}
