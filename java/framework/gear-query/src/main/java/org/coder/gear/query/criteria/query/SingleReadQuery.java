/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.coder.gear.query.free.query.ReadingConditions;
import org.coder.gear.query.gateway.PersistenceGateway;


/**
 * SingleReadQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class SingleReadQuery<E> extends ReadQuery<E>{
	
	/** the gateway */
	private final PersistenceGateway gateway;
	
	/**
	 * Constructor
	 * @param entityClass the entityClass
	 * @param em the entityManager
	 * @param gateway the gateway
	 */
	public SingleReadQuery(Class<E> entityClass, EntityManager em,PersistenceGateway gateway) {
		super(entityClass, em);
		this.gateway = gateway;
	}

	/**
	 * @see org.coder.gear.query.criteria.query.ReadQuery#doCallInternal(org.coder.gear.query.free.query.ReadingConditions)
	 */
	@Override
	protected E doCallInternal(ReadingConditions conditions) {
		List<E> result = gateway.getResultList(conditions);
		return result.isEmpty() ? null : result.get(0);
	}

}
