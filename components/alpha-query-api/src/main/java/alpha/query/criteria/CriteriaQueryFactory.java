/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria;

import javax.persistence.EntityManager;

import alpha.query.criteria.statement.StatementBuilderFactory;
import alpha.query.free.gateway.PersistenceGateway;



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
	 * Creates the query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> CriteriaReadQuery<T> createReadQuery(Class<T> entityClass, EntityManager em){
		return new CriteriaReadQuery<T>(entityClass,gateway,em,builderFactory);
	}
	
	/**
	 * Creates the updater.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T> CriteriaModifyQuery<T> createModifyQuery(Class<T> entityClass, EntityManager em){
		return new CriteriaModifyQuery<T>(entityClass,gateway,em,builderFactory);			
	}

}
