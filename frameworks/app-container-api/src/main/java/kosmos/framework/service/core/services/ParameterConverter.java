/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import java.util.Map;

import kosmos.framework.core.services.QueryRequest;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;


/**
 * parameter converter.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
abstract class ParameterConverter {
	
	/**
	 * Set parameter to query
	 * @param request the request
	 * @param query the query
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
		query.setQueryTimeout(request.getTimeoutSeconds());
	
		if(request.isNoDataError()){
			query.enableNoDataError();
		}
		return query;
	}
}
