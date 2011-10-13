/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal.impl;

import java.util.List;

import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.NativeQuery;
import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.api.free.ResultSetFilter;
import framework.sqlclient.internal.AbstractLocalNativeQueryEngine;

/**
 *　The query engine.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class QueryEngineImpl extends AbstractLocalNativeQueryEngine<InternalQueryImpl> implements NativeQuery{

	/** the EmptyHandler */
	private final EmptyHandler emptyHandler;
	
	/**
	 * @param delegate the query to delegate
	 * @param emtpyHandler the emtpyHandler to set
	 */
	public QueryEngineImpl(InternalQueryImpl delegate , EmptyHandler emptyHandler){
		super(delegate);
		this.emptyHandler = emptyHandler;
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		List<T> list = delegate.getResultList();
		if(nodataError && list.isEmpty()){
			emptyHandler.handleEmptyResult(delegate);
		}
		return list;
	}

	/**
	 * @see framework.sqlclient.internal.AbstractLocalQueryEngine#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#setFilter(framework.sqlclient.api.free.ResultSetFilter)
	 */
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		return (NativeResult<T>)delegate.getTotalResult();		
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		return delegate.getFetchResult();
	}

}
