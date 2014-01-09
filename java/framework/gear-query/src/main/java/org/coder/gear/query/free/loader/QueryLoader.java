/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.loader;

import java.util.Map;




/**
 * Builds the SQL.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface QueryLoader {
	
	/**
	 * Builds the SQL.
	 * 
	 * @param rowString　the SQL before loading
	 * @param queryId the queryId
	 * @return the SQL
	 */
	String build(String queryId ,String rowString);
	
	/**
	 * Evaluates the SQL.
	 * 
	 * @param query the SQL
	 * @param parameter the parameter to evaluate the if-statement
	 * @return the SQL
	 */
	String evaluate(String query , Map<String,Object> parameter , String queryId);

	/**
	 * Replaces ':value' to ?.
	 * 
	 * @param originalSql the SQL
	 * @param parameter the binding parameter
	 * @param queryId the queryId
	 * @return the replaced SQL
	 */
	PreparedQuery prepare(String originalSql, Map<String,Object> parameter,String queryId);

}


