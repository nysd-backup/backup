/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * SQLエンジンのパラメータ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class SQLParameter {

	/** SQL-ID */
	private String sqlId = null;
	
	/** SQL */
	private String sql = null;
	
	/** コメント */
	private StringBuilder comment = new StringBuilder();
	
	/** パラメータ */
	private Map<String,Object> parameter = new HashMap<String,Object>();
	
	/** if文用パラメータ */
	private Map<String,Object> branchParameter = new HashMap<String,Object>();
	
	/** そのままのSQLを使用するか否か */
	private boolean useRowSql = false;

	/**
	 * @param comment コメント
	 */
	public void addComment(String comment){
		this.comment.append(comment); 
	}
	
	/**
	 * @return コメント
	 */
	public String getComment(){
		return this.comment.toString();
	}
	
	/**
	 * @param param the param to set 
	 */
	public void setAllParameter(Map<String,Object> param){
		this.parameter = param;
	}
	
	/**
	 * @param param the param to set
	 */
	public void setAllBranchParameter(Map<String,Object> param){
		this.branchParameter = param;
	}
	
	/**
	 * @parma name パラメータ名
	 * @param param 値
	 */
	public void putParameter(String name,Object param) {
		parameter.put(name, param);
	}

	
	/**
	 * @param parameter the parameter to set
	 */
	public void putBranchParameter(String name ,Object param) {
		branchParameter.put(name, param);
	}
	

	/**
	 * @return the parameter
	 */
	public Map<String,Object> getBranchParameter() {
		return branchParameter;
	}

	/**
	 * @return the parameter
	 */
	public Map<String,Object> getParameter() {
		return parameter;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sqlId the sqlId to set
	 */
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	/**
	 * @return the sqlId
	 */
	public String getSqlId() {
		return sqlId;
	}
	
	/**
	 * @param useRowSql the useRowSql to set
	 */
	public void setUseRowSql(boolean useRowSql) {
		this.useRowSql = useRowSql;
	}

	/**
	 * @return the useRowSql
	 */
	public boolean isUseRowSql() {
		return useRowSql;
	}
	
}
