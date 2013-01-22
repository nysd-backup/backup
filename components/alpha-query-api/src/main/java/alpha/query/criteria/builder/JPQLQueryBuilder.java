/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.builder;

import java.util.Map;

import javax.persistence.LockModeType;


/**
 * JPQLQueryBuilder.
 *
 * @author yoshida-n
 * @version	created.
 */

public class JPQLQueryBuilder extends AbstractQueryBuilder{
	
	/**
	 * Constructor
	 */
	protected JPQLQueryBuilder(){
		
	}
	
	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withLock(javax.persistence.LockModeType, long)
	 */
	@Override
	public QueryBuilder withLock(LockModeType lock, long timeout) {
		return this;
	}	

	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryBuilder withSelect(Class<?> entityClass) {
		query.append(new StringBuilder(String.format("select e from %s e",entityClass.getSimpleName())));
		return this;
	}	
	
	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryBuilder withDelete(Class<?> entityClass) {
		query.append(String.format("delete from %s e ",entityClass.getSimpleName()));
		return this;
	}	
	
	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryBuilder withUpdate(Class<?> entityClass) {
		query.append(String.format("update %s e ",entityClass.getSimpleName()));
		return this;
	}	

	/**
	 * @see alpha.query.criteria.builder.QueryBuilder#withSelect(java.lang.Class)
	 */
	@Override
	public QueryBuilder withInsert(Class<?> entityClass,Map<String,Object> values) {
		throw new UnsupportedOperationException();
	}

}