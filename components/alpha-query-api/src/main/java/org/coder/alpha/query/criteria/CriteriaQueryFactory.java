/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.query.DeleteQuery;
import org.coder.alpha.query.criteria.query.FetchReadQuery;
import org.coder.alpha.query.criteria.query.ListReadQuery;
import org.coder.alpha.query.criteria.query.SingleReadQuery;
import org.coder.alpha.query.criteria.query.TotalReadQuery;
import org.coder.alpha.query.criteria.query.UpdateQuery;
import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.free.gateway.PersistenceGateway;


/**
 * The factory to create the query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CriteriaQueryFactory {
	
	/** the StatementBuilderFactory */
	private StatementBuilderFactory builderFactory;
	
	/** the StatementBuilderFactory */
	private PersistenceGateway gateway;
	
	/**
	 * @param builderFactory the builderFactory to set
	 */
	public void setBuilderFactory(StatementBuilderFactory builderFactory){
		this.builderFactory = builderFactory;
	}
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setPersistenceGateway(PersistenceGateway gateway){
		this.gateway = gateway;
	}

	/**
	 * Creates the reading query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return query
	 */
	public <E> ListReadQuery<E> createListReadQuery(Class<E> entityClass, EntityManager em){
		return new ListReadQuery<E>(entityClass,em,builderFactory,gateway);
	}
	
	/**
	 * Creates the reading query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return query
	 */
	public <E> SingleReadQuery<E> createSingleReadQuery(Class<E> entityClass, EntityManager em){
		return new SingleReadQuery<E>(entityClass,em,builderFactory,gateway);
	}

	/**
	 * Creates the reading query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return query
	 */
	public <E> FetchReadQuery<E> createFetchReadQuery(Class<E> entityClass, EntityManager em){
		return new FetchReadQuery<E>(entityClass,em,builderFactory,gateway);
	}
	
	/**
	 * Creates the reading query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return query
	 */
	public <E> TotalReadQuery<E> createTotalReadQuery(Class<E> entityClass, EntityManager em){
		return new TotalReadQuery<E>(entityClass,em,builderFactory,gateway);
	}

	/**
	 * Creates the deleting query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return query
	 */
	public <E> DeleteQuery<E> createDeleteQuery(Class<E> entityClass, EntityManager em){
		return new DeleteQuery<E>(entityClass,em,builderFactory,gateway);
	}
	
	/**
	 * Creates the updating query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return query
	 */
	public <E> UpdateQuery<E> createUpdateQuery(Class<E> entityClass, EntityManager em){
		return new UpdateQuery<E>(entityClass,em,builderFactory,gateway);
	}

}
