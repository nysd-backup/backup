/**
 * Copyright 2011 the original author
 */
package client.sql.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import client.sql.QueryParameter;


/**
 * The condition to execute SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class CriteriaQueryParameter<T> extends QueryParameter{

	
	/** the entityClass */
	private final Class<T> entityClass;
	
	/** the query hints */
	private Map<String,Object> hints = new HashMap<String,Object>();
	
	/** the conditions */
	private List<ExtractionCriteria> conditions = new ArrayList<ExtractionCriteria>();
	
	/** the updating values */
	private Map<String,Object> values = new LinkedHashMap<String,Object>();
	
	/**
	 * @param entityClass the entityClass
	 */
	public CriteriaQueryParameter(Class<T> entityClass){
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
	public List<ExtractionCriteria> getConditions() {
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
