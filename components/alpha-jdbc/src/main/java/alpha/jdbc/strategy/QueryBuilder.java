/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy;

import java.util.List;
import java.util.Map;

import alpha.jdbc.domain.PreparedQuery;



/**
 * Builds the SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface QueryBuilder {
	
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
	PreparedQuery prepare(String originalSql,List<Map<String,Object>> parameter, String wrapClause,String queryId);

}


