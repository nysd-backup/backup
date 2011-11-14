/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.query.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.ResultSetFilter;


/**
 * A request parameter of query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class QueryRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	/** the max size */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/** the timeout seconds */
	private int timeoutSeconds = 0;
	
	/** true:exception is thrown if the no result is found */
	private boolean noDataError = false;
	
	/** the class of the target query */
	private final Class<? extends Query> queryClass;
	
	/** the parameters */
	private Map<String,Object> param = new HashMap<String,Object>();
	
	/** the parameters for branch-statement */
	private Map<String,Object> branchParam = new HashMap<String,Object>();

	/** the hints */
	private Map<String,Object> hint = new HashMap<String,Object>();
		
	/** the filter for ResultSet */
	private ResultSetFilter filter;
	
	/**
	 * @param queryClass the class of the query
	 */
	public QueryRequest(Class<? extends Query> queryClass){
		this.queryClass = queryClass;
	}
	
	/**
	 * @param <T>　the type
	 * @return the class of the query
	 */
	@SuppressWarnings("unchecked")
	public <T extends Query> Class<T> getQueryClass(){
		return (Class<T>)queryClass;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(Map<String,Object> param) {
		this.param = param;
	}
	
	/**
	 * @param key the key to set
	 * @param value the value to set
	 */
	public void setParam(String key , Object value) {
		this.param.put(key, value);
	}

	/**
	 * @return the param
	 */
	public Map<String,Object> getParam() {
		return param;
	}

	/**
	 * @param branchParam the branchParam to set
	 */
	public void setBranchParam(Map<String,Object> branchParam) {
		this.branchParam = branchParam;
	}
	
	/**
	 * @param key the key to set
	 * @param value the value to set
	 */
	public void setBranchParam(String key , Object value) {
		this.branchParam.put(key, value);
	}

	/**
	 * @return the branchParam
	 */
	public Map<String,Object> getBranchParam() {
		return branchParam;
	}

	/**
	 * @param firstResult the firstResult to set
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(ResultSetFilter filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter
	 */
	public ResultSetFilter getFilter() {
		return filter;
	}

	/**
	 * @param hint the hint to set
	 */
	public void setHint(Map<String,Object> hint) {
		this.hint = hint;
	}
	
	/**
	 * @param key　the key of the hint
	 * @param value the hint value
	 */
	public void setHint(String key , Object value) {
		this.hint.put(key, value);
	}

	/**
	 * @return the hint
	 */
	public Map<String,Object> getHint() {
		return hint;
	}

	/**
	 * @param noDataError the noDataError to set
	 */
	public void setNoDataError(boolean noDataError) {
		this.noDataError = noDataError;
	}

	/**
	 * @return the noDataError
	 */
	public boolean isNoDataError() {
		return noDataError;
	}

	/**
	 * @param timeoutSeconds the timeoutSeconds to set
	 */
	public void setTimeoutSeconds(int timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}

	/**
	 * @return the timeoutSeconds
	 */
	public int getTimeoutSeconds() {
		return timeoutSeconds;
	}

}
