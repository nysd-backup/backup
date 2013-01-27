/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.free.ReadingConditions;
import org.coder.alpha.query.free.gateway.PersistenceGateway;

/**
 * ListReadQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ListReadQuery<E> extends ReadQuery<E,List<E>>{
	
	/** the persistenceGateway */
	private PersistenceGateway gateway;
	
	/** the max size */
	private int maxResults = -1;
	
	/**
	 * Constructor
	 * @param entityClass the entityClass
	 * @param em the entityManager
	 * @param builderFactory the builderFactory
	 * @param gateway the gateway
	 */
	public ListReadQuery(Class<E> entityClass, EntityManager em,
			StatementBuilderFactory builderFactory,PersistenceGateway gateway) {
		super(entityClass, em, builderFactory);
		this.gateway = gateway;
	}
	
	/**
	 * @param maxSize the maxSize to set
	 * @return self
	 */
	public ListReadQuery<E> setMaxResults(int maxResults){
		this.maxResults = maxResults;
		return this;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.ReadQuery#doCallInternal(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	protected List<E> doCallInternal(ReadingConditions conditions) {
		conditions.setMaxResults(maxResults);
		return gateway.getResultList(conditions);
	}
	
}
