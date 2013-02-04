/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import javax.persistence.EntityManager;

import org.coder.alpha.query.free.HitData;
import org.coder.alpha.query.free.ReadingConditions;
import org.coder.alpha.query.free.gateway.PersistenceGateway;

/**
 * TotalReadQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class TotalReadQuery<E> extends ReadQuery<E,HitData<E>>{
	
	/** the gateway */
	private PersistenceGateway gateway;
		
	/** the maxSize */
	private int maxResults = -1;
	
	/**
	 * Constructor
	 * @param entityClass
	 * @param em
	 * @param gateway
	 */
	public TotalReadQuery(Class<E> entityClass, EntityManager em,PersistenceGateway gateway) {
		super(entityClass, em);
		this.gateway = gateway;
	}
	
	/**
	 * @param maxSize the maxSize to set
	 * @return self
	 */
	public TotalReadQuery<E> setMaxResults(int maxResults){
		this.maxResults = maxResults;
		return this;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.ReadQuery#doCallInternal(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	protected HitData<E> doCallInternal(ReadingConditions conditions) {
		conditions.setMaxResults(maxResults);
		HitData<E> hitdata = gateway.getTotalResult(conditions);
		return hitdata;
	}
	
}
