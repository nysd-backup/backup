/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



/**
 * The condition to execute SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class BuildingProperties<T>{

	/** the entityClass */
	private final Class<T> entityClass;
	
	/** the query hints */
	private Map<String,Object> hints = new HashMap<String,Object>();
	
	/** the conditions */
	private List<Criteria<?>> conditions = new ArrayList<Criteria<?>>();
	
	/** the updating values */
	private Map<String,Object> values = new LinkedHashMap<String,Object>();
	
	/** the entity manager */
	private EntityManager entityManager;

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * @param entityClass the entityClass
	 */
	public BuildingProperties(Class<T> entityClass){
		this.entityClass = entityClass;
	}
	
	/**
	 * @return the hints
	 */
	public Map<String,Object> getHints(){
		return hints;
	}
	
	/**
	 * @param key the key of the hint
	 * @param value the hint value
	 */
	public void setHint(String key , Object value){
		hints.put(key, value);
	}
	
	/**
	 * @return the entity class
	 */
	public Class<T> getEntityClass(){
		return this.entityClass;
	}

	/**
	 * @return the conditions
	 */
	public List<Criteria<?>> getConditions() {
		return conditions;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}
