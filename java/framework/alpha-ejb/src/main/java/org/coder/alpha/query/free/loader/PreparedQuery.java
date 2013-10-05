/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.loader;

import java.util.List;


/**
 * A prepared query.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class PreparedQuery {

	/** the query statement */
	private String statement;
	
	/** the binding target */
	private final List<Object> bindList;
	
	/** the id */
	private final String queryId;
	
	/**
	 * @param statement the statement to set
	 * @param bindList the bindlist to set
	 * @param queryId the id to set
	 */
	public PreparedQuery(String statement, List<Object> bindList, String queryId){
		this.statement = statement;
		this.bindList = bindList;
		this.queryId = queryId;
	}
	
	/**
	 * @return the queryId
	 */
	public String getQueryId(){
		return this.queryId;
	}

	/**
	 * @return the bindList
	 */
	public List<Object> getBindList() {
		return bindList;
	}

	/**
	 * @return the statement
	 */
	public String getQueryStatement() {
		return statement;
	}

}
