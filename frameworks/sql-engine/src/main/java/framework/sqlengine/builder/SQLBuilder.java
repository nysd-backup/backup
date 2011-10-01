/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder;

import java.util.List;
import java.util.Map;


/**
 * Builds the SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface SQLBuilder {
	
	/**
	 * Builds the SQL.
	 * 
	 * @param rowString　the SQL before loading
	 * @param queryId the queryId
	 * @return the SQL
	 */
	public String build(String queryId ,String rowString);
	
	/**
	 * Evaluates the SQL.
	 * 
	 * @param query the SQL
	 * @param parameter the parameter to evaluate the if-statement
	 * @return the SQL
	 */
	public String evaluate(String query , Map<String,Object> parameter , String queryId);

	/**
	 * Replaces ':value' to ?.
	 * 
	 * @param originalSql the SQL
	 * @param parameter the binding parameter
	 * @param queryId the queryId
	 * @return the replaced SQL
	 */
	public String replaceToPreparedSql(String originalSql,Map<String,Object> parameter,List<Object> bindList, String queryId);
	
	/**
	 * Set the range.
	 * 
	 * @param sql the SQL
	 * @param firstResult the firstResult
	 * @param getSize the getting size
	 * @param bindList the binding target
	 * @return the SQL
	 */
	public String setRange(String sql , int firstResult , int getSize , List<Object> bindList);
	
	/**
	 * Convert the SQL to get count.
	 * 
	 * @param sql the SQL
	 * @return the SQL
	 */
	public String setCount(String sql);
}


