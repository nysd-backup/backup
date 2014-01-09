/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.free.query.Conditions;

/**
 * UpdateQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class UpdateQuery extends ModifyQuery{
	
	/** the updating target */
	private Map<String,Object> setValues = new HashMap<String,Object>();
	
	/**
	 * Low level API for String-based object.
	 * @param column the column 
	 * @return self
	 */
	public UpdateQuery set(String column , Object value){
		setValues.put(column, value);
		return this;
	}

	/**
	 * @see org.coder.gear.query.criteria.query.ModifyQuery#doCallInternal(org.coder.gear.query.free.query.Conditions, java.util.List)
	 */
	@Override
	protected Integer doCallInternal(Conditions conditions,
			List<Criteria> criterias) {
		
		String sql = builder.withSet(setValues).withWhere(criterias).buildUpdate(entityClass);
		conditions.setQueryId(entityClass.getSimpleName() + ".update");
		conditions.setSql(sql);
		//set
		for(Map.Entry<String, Object> v: setValues.entrySet()){
			conditions.getParam().put(v.getKey(),v.getValue());			
		}
		
		return gateway.executeUpdate(conditions);
	}

}
