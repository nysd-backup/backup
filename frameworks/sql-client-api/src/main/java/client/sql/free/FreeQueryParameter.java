/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The free parameter.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class FreeQueryParameter {
	
	/** the queryId */
	private String queryId = null;
	
	/** the SQL */
	private String sql = null;
	
	/** if true dont analyze the template*/
	private boolean useRowSql = true;
	
	/** the parameter for <code>PreparedStatement</code> */
	private Map<String,Object> param = new HashMap<String,Object>();
		
	/** the parameter for analyze the template */
	private Map<String,Object> branchParam = new HashMap<String,Object>();
	
	/** the JPA hint */
	private Map<String,Object> hints = new HashMap<String,Object>();
	
	/** the named querys name */
	private String name = null;
	
	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql){
		this.useRowSql = sql.charAt(0) != '@';
		this.sql = sql;
	}

	/**
	 * @return the useRowSql
	 */
	public boolean isUseRowSql() {
		return useRowSql;
	}

	/**
	 * @return the param
	 */
	public Map<String,Object> getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(Map<String,Object> param) {
		this.param = param;
	}

	/**
	 * @return the branchParam
	 */
	public Map<String,Object> getBranchParam() {
		return branchParam;
	}

	/**
	 * @param branchParam the branchParam to set
	 */
	public void setBranchParam(Map<String,Object> branchParam) {
		this.branchParam = branchParam;
	}

	/**
	 * @return the hints
	 */
	public Map<String,Object> getHints() {
		return hints;
	}

	/**
	 * @param hints the hints to set
	 */
	public void setHints(Map<String,Object> hints) {
		this.hints = hints;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * @return the queryId
	 */
	public String getQueryId() {
		return queryId;
	}

	/**
	 * @param queryId the queryId to set
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

}
