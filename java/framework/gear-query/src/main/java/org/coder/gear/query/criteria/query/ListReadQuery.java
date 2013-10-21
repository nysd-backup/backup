/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.coder.gear.query.free.query.ReadingConditions;
import org.coder.gear.query.gateway.PersistenceGateway;

/**
 * ListReadQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class ListReadQuery<E> extends ReadQuery<List<E>>{
	
	/** the persistenceGateway */
	private PersistenceGateway gateway;
	
	/** the max size */
	private int maxResults = -1;
	
	/**
	 * Constructor.
	 * 
	 * @param entityClass the entityClass
	 * @param em the entityManager
	 * @param builderFactory the builderFactory
	 * @param gateway the gateway
	 */
	public ListReadQuery(Class<E> entityClass, EntityManager em,PersistenceGateway gateway) {
		super(entityClass, em);
		this.gateway = gateway;
	}
	
	/**
	 * @param maxSize the maxSize to set
	 * @return self
	 */
	public ListReadQuery<E> limit(int maxResults){
		this.maxResults = maxResults;
		return this;
	}

	/**
	 * @see org.coder.gear.query.criteria.query.ReadQuery#doCallInternal(org.coder.gear.query.free.query.ReadingConditions)
	 */
	@Override
	protected List<E> doCallInternal(ReadingConditions conditions) {
		conditions.setMaxResults(maxResults);
		return gateway.getResultList(conditions);
	}
	
}
