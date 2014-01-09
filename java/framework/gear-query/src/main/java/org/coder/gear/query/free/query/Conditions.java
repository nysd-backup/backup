/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.query;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * The query parameter.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class Conditions{
	
	/** the queryId */
	private String queryId = null;
	
	/** the SQL */
	private String sql = null;
	
	/** if true dont analyze the template*/
	private boolean useRowSql = true;
	
	/** the parameter for <code>PreparedStatement</code> */
	private Map<String,Object> param = new HashMap<String,Object>();
		
	/** the JPA hint */
	private Map<String,Object> hints = new HashMap<String,Object>();

	/** the entity manager */
	private EntityManager entityManager;
	
	/** the resultType */
	private Class<?> resultType;
	
	/** the lock mode */
	private LockModeType lock = null;

	/** the max size */
	private int maxResults = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/**
	 * @return the maxResults
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * @param maxResults the maxResults to set
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * @param firstResult the firstResult to set
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * @return the lock
	 */
	public LockModeType getLockMode() {
		return lock;
	}

	/**
	 * @param lock the lock to set
	 */
	public void setLockMode(LockModeType lock) {
		this.lock = lock;
	}

	/**
	 * @return the resultType
	 */
	public Class<?> getResultType() {
		return resultType;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(Class<?> resultType) {
		this.resultType = resultType;
	}

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
	 * @param sql the sql to set
	 */
	public void setSql(String sql){
		this.useRowSql = sql == null || sql.isEmpty() || sql.charAt(0) != '@';
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
		setSql("@"+queryId);
	}

}
