/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria;

import javax.persistence.EntityManager;

import alpha.query.criteria.strategy.DataMapper;



/**
 * The factory to create the query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CriteriaQueryFactory {
	
	/** the DAO */
	private DataMapper internalOrmQuery;
	
	/**
	 * @param internalOrmQuery the internalOrmQuery to set
	 */
	public void setInternalOrmQuery(DataMapper internalOrmQuery){
		this.internalOrmQuery = internalOrmQuery;
	}

	/**
	 * Creates the query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> CriteriaReadQuery<T> createReadQuery(Class<T> entityClass, EntityManager em){
		return new CriteriaReadQuery<T>(entityClass,internalOrmQuery,em);
	}
	
	/**
	 * Creates the updater.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> CriteriaModifyQuery<T> createModifyQuery(Class<T> entityClass, EntityManager em){
		return new CriteriaModifyQuery<T>(entityClass,internalOrmQuery,em);			
	}

}
