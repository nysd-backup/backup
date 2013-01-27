/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.statement;

import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.criteria.SortKey;



/**
 * The query builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StatementBuilder {
	
	/**
	 * Creates the lock statement
	 * @param lock lock mode type
	 * @param timeout lock time out
	 * @return builder
	 */
	public StatementBuilder withLock(LockModeType lock,long timeout);

	/**
	 * Creates the delete statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public StatementBuilder withDelete(Class<?> entityClass);
	
	/**
	 * Creates the update statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public StatementBuilder withUpdate(Class<?> entityClass);
	
	/**
	 * Creates the select statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public StatementBuilder withSelect(Class<?> entityClass);
	
	/**
	 * Creates the insert statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public StatementBuilder withInsert(Class<?> entityClass,Map<String,Object> values);
	
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

	/**
	 * Creates the query
	 * @return  the statement
	 */
	public String build();
}