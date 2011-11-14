/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import java.util.List;

import kosmos.framework.api.query.services.NativeQueryService;
import kosmos.framework.api.query.services.QueryRequest;
import kosmos.framework.service.core.locator.ServiceLocator;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.QueryFactory;

/**
 * A native query service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NativeQueryServiceImpl implements NativeQueryService{

	/**
	 * @return the QueryFactory
	 */
	protected QueryFactory getQueryFactory(){
		return ServiceLocator.createDefaultClientQueryFactory();
	}
	
	/**
	 * @see kosmos.framework.api.query.services.NativeQueryService#count(kosmos.framework.api.query.services.QueryRequest)
	 */
	public int count(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.count();
	}
	
	/**
	 * @see kosmos.framework.api.query.services.NativeQueryService#getResultList(kosmos.framework.api.query.services.QueryRequest)
	 */
	public <T> List<T> getResultList(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.getResultList();
	}

	/**
	 * @see kosmos.framework.api.query.services.NativeQueryService#getTotalResult(kosmos.framework.api.query.services.QueryRequest)
	 */
	public <T> kosmos.framework.sqlclient.api.free.NativeResult<T> getTotalResult(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.getTotalResult();
	}

	/**
	 * @see kosmos.framework.api.query.services.NativeQueryService#getSingleResult(kosmos.framework.api.query.services.QueryRequest)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return (T)query.getSingleResult();
	}

	/**
	 * @see kosmos.framework.api.query.services.NativeQueryService#exists(kosmos.framework.api.query.services.QueryRequest)
	 */
	public boolean exists(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.exists();
	}

	/**
	 * @param request リクエスト
	 * @param query クエリ
	 * @return クエリ
	 */
	private AbstractNativeQuery setParameters(QueryRequest request,AbstractNativeQuery query){
		return ParameterConverter.setParameters(request, query);		
	}

	/**
	 * クエリの作成
	 * @param request
	 * @return
	 */
	private AbstractNativeQuery createQuery(QueryRequest request) {
		Class<AbstractNativeQuery> queryClass = request.getQueryClass();
		return getQueryFactory().createQuery(queryClass);
	}
	
	
}
