/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * The condition to execute SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class OrmParameter<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	/** the entityClass */
	private final Class<T> entityClass;
	
	/** the query hints */
	private Map<String,Object> hints = new HashMap<String,Object>();
	
	/** the conditions */
	private List<WhereCondition> conditions = new ArrayList<WhereCondition>();
	
	/** the updating values */
	private Map<String,Object> values = new LinkedHashMap<String,Object>();
	
	/** the filter to search */
	private String filterString = null;
	
	/** the parameter only for easy query */
	private Object[] easyParams = new Object[0];
	
	/**
	 * @param entityClass the entityClass
	 */
	public OrmParameter(Class<T> entityClass){
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
	public List<WhereCondition> getConditions() {
		return conditions;
	}

	/**
	 * @param filterString the filterString
	 */
	public void setFilterString(String filterString){
		this.filterString = filterString;
	}

	/**
	 * @return the filterSting
	 */
	public String getFilterString(){
		return this.filterString;
	}

	/**
	 * @param easyParams the easyParams to set
	 */
	public void setEasyParams(Object[] easyParams) {
		this.easyParams = easyParams;
	}

	/**
	 * @return the easyParams
	 */
	public Object[] getEasyParams() {
		return easyParams;
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
