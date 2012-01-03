/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder;

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
	public String replaceToPreparedSql(String originalSql,List<Map<String,Object>> parameter,List<List<Object>> bindList, String queryId);

	
	/**
	 * Convert the SQL to get count.
	 * 
	 * @param sql the SQL
	 * @return the SQL
	 */
	public String setCount(String sql);
}


