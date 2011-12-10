/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kosmos.framework.core.services.ConsecutiveQueryService;
import kosmos.framework.core.services.NativeQueryService;
import kosmos.framework.core.services.OrmQueryService;
import kosmos.framework.core.services.QueryRequest;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.sqlclient.api.orm.OrmCondition;
import kosmos.framework.utility.ReflectionUtils;

/**
 * A consecutive SQL service.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConsecutiveQueryServiceImpl implements ConsecutiveQueryService{
	
	/**
	 * @see kosmos.framework.core.services.ConsecutiveQueryService#execute(java.io.Serializable[])
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<List<Object>> getResultLists(Serializable... request) {

		OrmQueryService ormQueryService = ServiceLocator.lookupByInterface(OrmQueryService.class);		
		NativeQueryService nativeQueryService = ServiceLocator.lookupByInterface(NativeQueryService.class);
		
		List<List<Object>> results = new ArrayList<List<Object>>();	
		for(Serializable q : request){		
			
			if (q instanceof OrmCondition<?>){
				results.add(ormQueryService.getResultList((OrmCondition<?>)q));
			}else if(q instanceof QueryRequest){
				results.add(nativeQueryService.getResultList((QueryRequest)q));
			}else {
				throw new IllegalArgumentException();
			}
		}
		return results;
	}
	
	/**
	 * @see kosmos.framework.core.services.ConsecutiveQueryService#execute(java.io.Serializable[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<List<Object>> getChainedResultLists(QueryRequest... request) {
		
		NativeQueryService nativeQueryService = ServiceLocator.lookupByInterface(NativeQueryService.class);
		
		List<List<Object>> results = new ArrayList<List<Object>>();
		List<Object> previous = null;		
		for(QueryRequest q : request){		
			if(previous != null){
				Object first = previous.get(0);
				if(first instanceof Map){
					setToMap((Map<String,Object>)first,q);
				}else{
					setToBean(first,q);
				}
			}
			previous = nativeQueryService.getResultList(q);
			results.add(previous);
		}
		return results;
	}
	
	/**
	 * Sets the first result to next condition.
	 * 
	 * @param data the previous result
	 * @param query the query
	 */
	private void setToMap(Map<String,Object> data,QueryRequest query){
		query.getBranchParam().putAll(data);
		query.getParam().putAll(data);
	}
	
	/**
	 * Sets the first result to next condition.
	 * 
	 * @param data the previous result
	 * @param query the query
	 */
	private void setToBean(Object data,QueryRequest query){
		Method[] methods = data.getClass().getDeclaredMethods();
		for(Method m : methods){
			if(!ReflectionUtils.isGetter(m)){
				continue;
			}
			String key = ReflectionUtils.getPropertyNameFromGetter(m);
			Object value = ReflectionUtils.invokeMethod(m, data);;
			query.getBranchParam().put(key, value);
			query.getParam().put(key, value);
		}
	}

}
