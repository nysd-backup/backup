/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.List;
import java.util.Map;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.free.query.Conditions;

/**
 * ModifyQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public abstract class ModifyQuery extends CriteriaQuery<Integer>{
	
	/** entity class. */
	protected Class<?> entityClass;
	
	/**
	 * @param entityClass to set
	 */
	public void setEntityClass(Class<?> entityClass){
		this.entityClass = entityClass;
	}

	/**
	 * @see org.coder.gear.query.criteria.query.CriteriaQuery#doCall(org.coder.alpha.query.criteria.statement.StatementBuilder)
	 */
	@Override
	protected Integer doCall(List<Criteria> criterias){
		Conditions parameter = new Conditions();		
		for(Criteria criteria : criterias){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: getHints().entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		return doCallInternal(parameter,criterias);
	}
	
	/**
	 * Calls query.
	 * 
	 * @param conditions the conditions
	 * @param criterias the criteria
	 * @param entityClass the entityClass
	 * @return the result
	 */
	protected abstract Integer doCallInternal(Conditions conditions,List<Criteria> criterias);
	
}
