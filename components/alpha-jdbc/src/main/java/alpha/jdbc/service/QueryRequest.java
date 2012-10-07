/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The base of the parameter
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class QueryRequest{
	
	/** the parameter */
	private Map<String,Object> parameter = new HashMap<String,Object>();
	
	/** the sqlId */
	private String sqlId = null;
	
	/** the SQL */
	private String sql = null;
	
	/** the comment */
	private StringBuilder comment = new StringBuilder();
	
	/** the use row sql */
	private boolean useRowSql = false;
	
	/** the timeout */
	private int timeoutSeconds = 0;
	
	/** the wrapping clause */
	private String wrappingClause = null;

	/**
	 * @param comment the comment to set
	 */
	public void addComment(String comment){
		this.comment.append(comment); 
	}
	
	/**
	 * @return the comment
	 */
	public String getComment(){
		return this.comment.toString();
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
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	/**
	 * @param param the param to set 
	 */
	public void setAllParameter(Map<String,Object> param){
		this.parameter = param;
	}
	
	/**
	 * @param name パラメータ名
	 * @param param 値
	 */
	public void putParameter(String name,Object param) {
		parameter.put(name, param);
	}
	
	/**
	 * @return the parameter
	 */
	public Map<String,Object> getParameter() {
		return parameter;
	}

	/**
	 * @return the wrapClause
	 */
	public String getWrappingClause() {
		return wrappingClause;
	}

	/**
	 * @param wrapClause the wrapClause to set
	 */
	public void setWrappingClause(String wrappingClause) {
		this.wrappingClause = wrappingClause;
	}

}
