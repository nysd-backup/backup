/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.orm.strategy;

import java.util.List;
import java.util.Map;

import alpha.sqlclient.orm.CriteriaReadQueryParameter;
import alpha.sqlclient.orm.ExtractionCriteria;





/**
 * The builder to create the SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface SQLStatementBuilder {

	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createSelect(CriteriaReadQueryParameter<?> condition);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createInsert(Class<?> entityClass, Map<String,Object> values);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createUpdate(Class<?> entityClass,List<ExtractionCriteria<?>> where, Map<String,Object> set);
	
	/**
	 * Creates the SQL statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String createDelete(Class<?> entityClass, List<ExtractionCriteria<?>> where);

}
