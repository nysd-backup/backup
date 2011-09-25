/**
 * Use is subject to license terms.
 */
package framework.service.core.services;

import java.util.Map;

import framework.api.query.services.QueryRequest;
import framework.sqlclient.api.free.AbstractNativeQuery;

/**
 * パラメータ設定.
 *
 * @author yoshida-n
 * @version	2011/06/15 created.
 */
abstract class ParameterConverter {
	
	/**
	 * パラメータを設定する。
	 * @param request リクエスト
	 * @param query クエリ
	 */
	public static AbstractNativeQuery setParameters(QueryRequest request,AbstractNativeQuery query){

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
	
		if(request.isNoDataError()){
			query.enableNoDataError();
		}
		return query;
	}
}
