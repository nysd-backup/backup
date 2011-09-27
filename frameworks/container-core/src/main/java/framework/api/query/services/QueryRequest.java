/**
 * Copyright 2011 the original author
 */
package framework.api.query.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.TemporalType;

import framework.sqlclient.api.Query;
import framework.sqlclient.api.free.ResultSetFilter;

/**
 * クエリリクエストパラメータ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class QueryRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 最大件数 */
	private int maxSize = 0;
	
	/** 先頭位置 */
	private int firstResult = 0;
	
	/** 検索結果0件時システムエラー */
	private boolean noDataError = false;
	
	/** クエリクラス */
	private final Class<? extends Query> queryClass;
	
	/** パラメータ */
	private Map<String,Object> param = new HashMap<String,Object>();
	
	/** if文用パラメータ */
	private Map<String,Object> branchParam = new HashMap<String,Object>();
	
	/** 日付用*/
	private Map<String,TemporalType> temporal = new HashMap<String,TemporalType>();
	
	/** ヒント */
	private Map<String,Object> hint = new HashMap<String,Object>();
		
	/** フィルター */
	private ResultSetFilter filter;
	
	/**
	 * @param queryClass クエリクラス
	 */
	public QueryRequest(Class<? extends Query> queryClass){
		this.queryClass = queryClass;
	}
	
	/**
	 * @param <T>　型
	 * @return クエリクラス
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
	 * ヒント句設定.
	 * @param key　キー
	 * @param value 値
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
	 * @param temporal the temporal to set
	 */
	public void setTemporal(Map<String,TemporalType> temporal) {
		this.temporal = temporal;
	}

	/**
	 * @param key the key to set
	 * @param value the value to set
	 */
	public void setTemporal(String key , TemporalType value) {
		this.temporal.put(key, value);
	}
	
	/**
	 * @return the temporal
	 */
	public Map<String,TemporalType> getTemporal() {
		return temporal;
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

}
