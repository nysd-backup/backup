/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.statement;

import java.util.List;
import java.util.Map;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.SortKey;



/**
 * The query builder.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface StatementBuilder {
	
	/**
	 * Creates the delete statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String buildDelete(Class<?> entityClass);
	
	/**
	 * Creates the update statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String buildUpdate(Class<?> entityClass);
	
	/**
	 * Creates the select statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public String buildSelect(Class<?> entityClass);
		
	/**
	 * Creates the where statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public StatementBuilder withWhere(List<Criteria> criteria);
	
	/**
	 * Creates the order by statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public StatementBuilder withOrderBy(List<SortKey> sortKey);
	
	/**
	 * Creates the set statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public StatementBuilder withSet(Map<String,Object> set);

}