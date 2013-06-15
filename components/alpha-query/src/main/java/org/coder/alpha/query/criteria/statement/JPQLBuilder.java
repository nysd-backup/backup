/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.statement;

import java.util.Map;

import javax.persistence.LockModeType;


/**
 * JPQLBuilder.
 *
 * @author yoshida-n
 * @version	created.
 */

public class JPQLBuilder extends AbstractStatementBuilder{
	
	/**
	 * Constructor
	 */
	protected JPQLBuilder(){
		
	}
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withLock(javax.persistence.LockModeType, long)
	 */
	@Override
	public StatementBuilder withLock(LockModeType lock, long timeout) {
		return this;
	}	

	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public StatementBuilder withSelect(Class<?> entityClass) {
		query.append(new StringBuilder(String.format("select e from %s e",entityClass.getSimpleName())));
		return this;
	}	
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public StatementBuilder withDelete(Class<?> entityClass) {
		query.append(String.format("delete from %s e ",entityClass.getSimpleName()));
		return this;
	}	
	
	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public StatementBuilder withUpdate(Class<?> entityClass) {
		query.append(String.format("update %s e ",entityClass.getSimpleName()));
		return this;
	}	

	/**
	 * @see org.coder.alpha.query.criteria.statement.StatementBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public StatementBuilder withInsert(Class<?> entityClass,Map<String,Object> values) {
		throw new UnsupportedOperationException();
	}

}