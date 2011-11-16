/**
 * Copyright 2011 the original author
 */
package kosmos.framework.client.core.api.query;

import java.util.List;

import kosmos.framework.core.services.NativeQueryService;
import kosmos.framework.core.services.QueryRequest;
import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.NativeQuery;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;

/**
 * The native query engine for WEB.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class WebNativeQueryEngine implements NativeQuery{
	
	/** the service */
	private NativeQueryService service;
	
	/**　DTO */
	private final QueryRequest request;

	/**
	 * @param queryClass the queryClass to set
	 * @param service the service
	 */
	WebNativeQueryEngine(Class<? extends Query> queryClass,NativeQueryService service){
		request = new QueryRequest(queryClass);
		this.service = service;
	}
	
	/**
	 * @return the request
	 */
	public QueryRequest getRequest(){
		return request;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		return (NativeResult<T>)service.getTotalResult(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		return (List<T>)service.getResultList(request);		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		throw new UnsupportedOperationException();	
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)service.getSingleResult(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		request.setBranchParam(arg0,arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setParameter(String arg0, Object arg1) {
		request.setParam(arg0,arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <T extends Query> T enableNoDataError() {
		request.setNoDataError(true);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		request.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		request.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return service.exists(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return service.count(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setFilter(kosmos.framework.sqlclient.api.free.ResultSetFilter)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		request.setFilter(filter);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.NativeQuery#setQueryTimeout(int)
	 */
	@Override
	public <T extends NativeQuery> T setQueryTimeout(int seconds) {
		request.setTimeoutSeconds(seconds);
		return (T)this;
	}

}
