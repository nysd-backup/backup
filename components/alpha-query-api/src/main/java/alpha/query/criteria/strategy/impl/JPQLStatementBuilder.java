/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.strategy.impl;

import java.util.Map;

import javax.persistence.LockModeType;

import alpha.query.criteria.strategy.AbstractStatementBuilder;
import alpha.query.criteria.strategy.QueryStatementBuilder;

/**
 * JPQLStatementBuilder.
 *
 * @author yoshida-n
 * @version	created.
 */

public class JPQLStatementBuilder extends AbstractStatementBuilder{
	
	/**
	 * @see alpha.query.criteria.strategy.QueryStatementBuilder#withLock(javax.persistence.LockModeType, long)
	 */
	@Override
	public QueryStatementBuilder withLock(LockModeType lock, long timeout) {
		return this;
	}	

	/**
	 * @see alpha.query.criteria.strategy.QueryStatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryStatementBuilder withSelect(Class<?> entityClass) {
		query.append(new StringBuilder(String.format("select e from %s e",entityClass.getSimpleName())));
		return this;
	}	
	
	/**
	 * @see alpha.query.criteria.strategy.QueryStatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryStatementBuilder withDelete(Class<?> entityClass) {
		query.append(String.format("delete from %s e ",entityClass.getSimpleName()));
		return this;
	}	
	
	/**
	 * @see alpha.query.criteria.strategy.QueryStatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryStatementBuilder withUpdate(Class<?> entityClass) {
		query.append(String.format("update %s e ",entityClass.getSimpleName()));
		return this;
	}	

	/**
	 * @see alpha.query.criteria.strategy.QueryStatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryStatementBuilder withInsert(Class<?> entityClass,Map<String,Object> values) {
		throw new UnsupportedOperationException();
	}

}