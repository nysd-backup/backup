/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free.impl;

import java.util.List;

import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.NativeQuery;
import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.api.free.ResultSetFilter;
import framework.sqlclient.internal.AbstractLocalNativeQueryEngine;

/**
 *　JPAのNativeQueryを使用するクエリエンジン.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalJPANativeQueryEngine extends AbstractLocalNativeQueryEngine<InternalNativeJPAQueryImpl> implements NativeQuery{

	/** 0件時処理 */
	private final EmptyHandler emptyHandler;
	
	/**
	 * @param delegate クエリ
	 * @param emptyHandler 0件処理
	 */
	public LocalJPANativeQueryEngine(InternalNativeJPAQueryImpl delegate , EmptyHandler emptyHandler){
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
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count(){
		throw new UnsupportedOperationException();
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
	@SuppressWarnings("rawtypes")
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		throw new UnsupportedOperationException();
	}

}
