/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The condition to update.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrmUpdateParameter<T> extends OrmParameter<T>{

	private static final long serialVersionUID = 1L;

	private Map<String,Object> values = new LinkedHashMap<String,Object>();
	
	private List<List<WhereCondition>> batchCondition = new ArrayList<List<WhereCondition>>();
	
	private List<Map<String,Object>> batchValues = new ArrayList<Map<String,Object>>();
	
	/**
	 * Adds the batch parameter.
	 */
	public void addBatch(){
		Map<String,Object> v = new LinkedHashMap<String,Object>(values);
		values.clear();
		batchValues.add(v);
		
		List<WhereCondition> c = new ArrayList<WhereCondition>(getConditions());
		getConditions().clear();
		batchCondition.add(c);
	}
	
	/**
	 * @return batch condition
	 */
	public List<List<WhereCondition>> getBatchCondition(){
		return batchCondition;
	}
	
	/**
	 * @return batch values
	 */
	public List<Map<String,Object>> getBatchValues(){
		return batchValues;
	}
	
	/**
	 * @return the current values
	 */
	public Map<String,Object> getCurrentValues(){
		return values;
	}
	
	/**
	 * @param key the key
	 * @param value the value
	 */
	public void set(String key ,Object value){
		values.put(key, value);
	}

	/**
	 * @param entityClass the entityClass to set
	 */
	public OrmUpdateParameter(Class<T> entityClass) {
		super(entityClass);
	}

}
