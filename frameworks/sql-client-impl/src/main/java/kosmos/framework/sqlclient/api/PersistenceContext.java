/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kosmos.framework.sqlclient.api.free.BatchUpdate;
import kosmos.framework.sqlclient.api.free.BatchUpdateFactory;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceContext {
	
	private Map<String,List<FreeUpdateParameter>> cache = new HashMap<String,List<FreeUpdateParameter>>();
	
	private boolean enabled = false;
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	/**
	 * @return
	 */
	public boolean isEnabled(){
		return this.enabled;
	}
	
	/**
	 * @param entity
	 * @param parameter
	 */
	public void add(Object entity, FreeUpdateParameter parameter){
		if(!isEnabled()){
			throw new IllegalStateException("cache is disabled");
		}
		String key = entity.getClass().getName();
		if(cache.containsKey(key)){
			List<FreeUpdateParameter> params = cache.get(key);
			params.add(parameter);
		}else{
			List<FreeUpdateParameter> params = new ArrayList<FreeUpdateParameter>();
			params.add(parameter);
			cache.put(key, params);
		}
	}
	
	/**
	 * @param factory
	 */
	public void flush(BatchUpdateFactory factory) {
		
		try{
			if(!isEnabled()){				
				throw new IllegalStateException("cache is disabled");
			}
			for(List<FreeUpdateParameter> e: cache.values()){
				BatchUpdate updater = factory.createBatchUpdate();		
				for(FreeUpdateParameter p : e){
					updater.addBatch(p);
				}			
				e.clear();
				updater.executeBatch();				
			}
		}finally{
			cache.clear();
		}
	}	

}
