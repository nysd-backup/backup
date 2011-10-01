/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.orm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The condition to execute SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmCondition<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	/** the entityClass */
	private final Class<T> entityClass;
	
	/** if true raise the exception */
	private boolean noDataErrorEnabled = false;
	
	/** the max size to be able to search */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/** the query hints */
	private Map<String,Object> hints = new HashMap<String,Object>();
	
	/** the keys of the sorting */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();
	
	/** the conditions */
	private List<WhereCondition> conditions = new ArrayList<WhereCondition>();
	
	/** the filter to search */
	private String filterString = null;
	
	/** the order to sort */
	private String orderString = null;
	
	/** the parameter only for easy query */
	private Object[] easyParams = new Object[0];

	
	/**
	 * @param entityClass the entityClass
	 */
	public OrmCondition(Class<T> entityClass){
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
	 * @return 自オブジェクト
	 */
	public OrmCondition<T> setNoDataErrorEnabled(){
		this.noDataErrorEnabled = true;
		return this;
	}
	
	/**
	 * @return if true raise the exception
	 */
	public boolean isNoDataErrorEnabled(){
		return this.noDataErrorEnabled;
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
	 * @return the sort key
	 */
	public List<SortKey> getSortKeys() {
		return sortKeys;
	}
	
	/**
	 * @param maxSize the maxSize
	 */
	public void setMaxSize(int maxSize){
		this.maxSize = maxSize;
	}
	
	/**
	 * @return the maxSize
	 */
	public int getMaxSize(){
		return this.maxSize;
	}
	
	/**
	 * @param firstResult the firstResult
	 */
	public void setFirstResult(int firstResult){
		this.firstResult = firstResult;
	}
	
	/**
	 * @return the firstResult
	 */
	public int getFirstResult(){
		return this.firstResult;
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
	 * @param orderString the orderString to set
	 */
	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	/**
	 * @return the orderString
	 */
	public String getOrderString() {
		return orderString;
	}
}
