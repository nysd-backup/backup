/**
 * Copyright 2011 the original author
 */
package framework.service.core.services;

import java.util.List;
import framework.api.query.services.NativeQueryService;
import framework.api.query.services.QueryRequest;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.QueryFactory;

/**
 * リモートからのクエリ実行のインターフェース.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNativeQueryService implements NativeQueryService{

	/** クエリファクトリ */
	protected abstract QueryFactory getQueryFactory();
	
	/**
	 * @see framework.api.query.services.NativeQueryService#count(framework.api.query.services.QueryRequest)
	 */
	public int count(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.count();
	}
	
	/**
	 * @see framework.api.query.services.NativeQueryService#getResultList(framework.api.query.services.QueryRequest)
	 */
	public <T> List<T> getResultList(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.getResultList();
	}

	/**
	 * @see framework.api.query.services.NativeQueryService#getTotalResult(framework.api.query.services.QueryRequest)
	 */
	public <T> framework.sqlclient.api.free.NativeResult<T> getTotalResult(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return query.getTotalResult();
	}

	/**
	 * @see framework.api.query.services.NativeQueryService#getSingleResult(framework.api.query.services.QueryRequest)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(QueryRequest request) {
		AbstractNativeQuery query = setParameters(request,createQuery(request));
		return (T)query.getSingleResult();
	}

	/**
	 * @see framework.api.query.services.NativeQueryService#exists(framework.api.query.services.QueryRequest)
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
