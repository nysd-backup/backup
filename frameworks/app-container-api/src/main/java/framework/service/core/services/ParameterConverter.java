/**
 * Copyright 2011 the original author
 */
package framework.service.core.services;

import java.util.Map;

import framework.api.query.services.QueryRequest;
import framework.sqlclient.api.free.AbstractNativeQuery;

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
	
		if(request.isNoDataError()){
			query.enableNoDataError();
		}
		return query;
	}
}
