/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import java.util.List;
import java.util.Map;

import kosmos.framework.core.services.NativeQueryService;
import kosmos.framework.core.services.QueryRequest;
import kosmos.framework.service.core.activation.ServiceLocator;
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
	 * @see kosmos.framework.core.services.NativeQueryService#count(kosmos.framework.core.services.QueryRequest)
	 */
	public int count(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.count();
	}
	
	/**
	 * @see kosmos.framework.core.services.NativeQueryService#getResultList(kosmos.framework.core.services.QueryRequest)
	 */
	public <T> List<T> getResultList(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.getResultList();
	}

	/**
	 * @see kosmos.framework.core.services.NativeQueryService#getTotalResult(kosmos.framework.core.services.QueryRequest)
	 */
	public kosmos.framework.sqlclient.api.free.NativeResult getTotalResult(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.getTotalResult();
	}

	/**
	 * @see kosmos.framework.core.services.NativeQueryService#getSingleResult(kosmos.framework.core.services.QueryRequest)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return (T)query.getSingleResult();
	}

	/**
	 * @see kosmos.framework.core.services.NativeQueryService#exists(kosmos.framework.core.services.QueryRequest)
	 */
	public boolean exists(QueryRequest request) {
		return getSingleResult(request) != null;
	}

	/**
	 * @param request リクエスト
	 * @param query クエリ
	 * @return クエリ
	 */
	private AbstractNativeQuery setParameters(QueryRequest request,AbstractNativeQuery query){
		//Branch
		for(Map.Entry<String, Object> e : request.getBranchParam().entrySet()){
			query.setBranchParameter(e.getKey(), e.getValue());
		}

		//Param
		for(Map.Entry<String, Object> e : request.getParam().entrySet()){
			String key = e.getKey();
			query.setParameter(key, e.getValue());
		}
		query.setFirstResult(request.getFirstResult());
		query.setMaxResults(request.getMaxSize());
		Map<String,Object> hint = request.getHint();
		for(Map.Entry<String, Object> h : hint.entrySet()){
			query.setHint(h.getKey(),h.getValue());
		}
		return query;	
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
