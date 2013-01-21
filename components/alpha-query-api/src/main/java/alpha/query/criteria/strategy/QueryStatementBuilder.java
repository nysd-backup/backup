/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.strategy;

import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import alpha.query.criteria.Criteria;
import alpha.query.criteria.SortKey;


/**
 * The query builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface QueryStatementBuilder {
	
	/**
	 * Creates the lock statement
	 * @param lock lock mode type
	 * @param timeout lock time out
	 * @return builder
	 */
	public QueryStatementBuilder withLock(LockModeType lock,long timeout);

	/**
	 * Creates the delete statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public QueryStatementBuilder withDelete(Class<?> entityClass);
	
	/**
	 * Creates the update statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public QueryStatementBuilder withUpdate(Class<?> entityClass);
	
	/**
	 * Creates the select statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public QueryStatementBuilder withSelect(Class<?> entityClass);
	
	/**
	 * Creates the insert statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public QueryStatementBuilder withInsert(Class<?> entityClass,Map<String,Object> values);
	
	/**
	 * Creates the where statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public QueryStatementBuilder withWhere(List<Criteria<?>> criteria);
	
	/**
	 * Creates the order by statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public QueryStatementBuilder withOrderBy(List<SortKey> sortKey);
	
	/**
	 * Creates the set statement.
	 * 
	 * @param condition the condition
	 * @return　the statement
	 */
	public QueryStatementBuilder withSet(Map<String,Object> set);

	/**
	 * Creates the query
	 * @return  the statement
	 */
	public String build();
}