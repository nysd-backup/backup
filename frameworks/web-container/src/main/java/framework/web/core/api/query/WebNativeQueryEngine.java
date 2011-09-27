/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.query;

import java.util.List;
import framework.api.query.services.NativeQueryService;
import framework.api.query.services.QueryRequest;
import framework.sqlclient.api.Query;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.NativeQuery;
import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.api.free.ResultSetFilter;

/**
 * WEBコンテナ用NativeQueryのエンジン.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class WebNativeQueryEngine implements NativeQuery{
	
	/** クエリの実行体 */
	private NativeQueryService service;
	
	/**　DTO */
	private final QueryRequest request;
	
	/**
	 * コンストラクタ
	 */
	WebNativeQueryEngine(Class<? extends Query> queryClass,NativeQueryService service){
		request = new QueryRequest(queryClass);
		this.service = service;
	}
	
	/**
	 * @return リクエストデータ
	 */
	public QueryRequest getRequest(){
		return request;
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		return (NativeResult<T>)service.getTotalResult(request);
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		return (List<T>)service.getResultList(request);		
	}
	
	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		throw new UnsupportedOperationException();	
	}

	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)service.getSingleResult(request);
	}

	/**
	 * @see framework.sqlclient.api.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		request.setBranchParam(arg0,arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.FreeQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setParameter(String arg0, Object arg1) {
		request.setParam(arg0,arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <T extends Query> T enableNoDataError() {
		request.setNoDataError(true);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		request.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		request.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return service.exists(request);
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return service.count(request);
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#setFilter(framework.sqlclient.api.free.ResultSetFilter)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		request.setFilter(filter);
		return (T)this;
	}

}
