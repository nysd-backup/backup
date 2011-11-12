/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.free.impl;

import java.util.List;

import kosmos.framework.jpqlclient.internal.free.AbstractInternalJPANativeQuery;
import kosmos.framework.sqlclient.api.EmptyHandler;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.NativeQuery;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;
import kosmos.framework.sqlclient.internal.AbstractLocalNativeQueryEngine;


/**
 *　The native query engine for JPA.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalJPANativeQueryEngine extends AbstractLocalNativeQueryEngine<AbstractInternalJPANativeQuery<?>> implements NativeQuery{

	/** the <code>EmptyHandler</code> */
	private final EmptyHandler emptyHandler;
	
	/**
	 * @param delegate the delegate to set
	 * @param emptyHandler the emptyHandler to set
	 */
	public LocalJPANativeQueryEngine(AbstractInternalJPANativeQuery<?> delegate , EmptyHandler emptyHandler){
		super(delegate);
		this.emptyHandler = emptyHandler;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
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
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count(){
		return delegate.count();
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.AbstractLocalQueryEngine#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setFilter(kosmos.framework.sqlclient.api.free.ResultSetFilter)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		return (NativeResult<T>)delegate.getTotalResult();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		return delegate.getFetchResult();
	}

}
