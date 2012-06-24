/**
 * Copyright 2011 the original author
 */
package client.sql.orm;

import javax.persistence.EntityManager;

import client.sql.orm.strategy.InternalOrmQuery;


/**
 * The factory to create the query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryFactory {
	
	/** the DAO */
	private InternalOrmQuery internalOrmQuery;
	
	/**
	 * @param internalOrmQuery the internalOrmQuery to set
	 */
	public void setInternalOrmQuery(InternalOrmQuery internalOrmQuery){
		this.internalOrmQuery = internalOrmQuery;
	}

	/**
	 * Creates the query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> OrmSelect<T> createSelect(Class<T> entityClass, EntityManager em){
		return new OrmSelect<T>(entityClass,internalOrmQuery,em);
	}
	
	/**
	 * Creates the updater.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> OrmUpdate<T> createUpdate(Class<T> entityClass, EntityManager em){
		return new OrmUpdate<T>(entityClass,internalOrmQuery,em);			
	}

}
