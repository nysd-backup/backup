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
 * ORマッピング用の検索条件.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmCondition<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 検索対象エンティティクラス */
	private final Class<T> entityClass;
	
	/** 検索結果0件時エラー */
	private boolean noDataErrorEnabled = false;
	
	/** 最大件数 */
	private int maxSize = 0;
	
	/** 先頭位置 */
	private int firstResult = 0;
	
	/** ヒント */
	private Map<String,Object> hints = new HashMap<String,Object>();
	
	/** ソートキー */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();
	
	/** エンティティクエリのWHERE句条件 */
	private List<WhereCondition> conditions = new ArrayList<WhereCondition>();
	
	/** 条件 */
	private String filterString = null;
	
	/** ソート */
	private String orderString = null;
	
	/** パラメータ */
	private Object[] easyParams = new Object[0];

	
	/**
	 * @param entityClass 検索対象クラス
	 */
	public OrmCondition(Class<T> entityClass){
		this.entityClass = entityClass;
	}
	
	/**
	 * @return ヒント句
	 */
	public Map<String,Object> getHints(){
		return hints;
	}
	
	/**
	 * @param key キー
	 * @param value 値
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
	 * @return 検索結果0件時エラー
	 */
	public boolean isNoDataErrorEnabled(){
		return this.noDataErrorEnabled;
	}
	
	/**
	 * @return エンティティクラス
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
	 * @param maxSize 最大件数
	 */
	public void setMaxSize(int maxSize){
		this.maxSize = maxSize;
	}
	
	/**
	 * @return 最大件数
	 */
	public int getMaxSize(){
		return this.maxSize;
	}
	
	/**
	 * @param firstResult 先頭位置
	 */
	public void setFirstResult(int firstResult){
		this.firstResult = firstResult;
	}
	
	/**
	 * @return 先頭位置
	 */
	public int getFirstResult(){
		return this.firstResult;
	}

	/**
	 * @param filterString 条件
	 */
	public void setFilterString(String filterString){
		this.filterString = filterString;
	}

	/**
	 * @return 条件
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
