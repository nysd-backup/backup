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
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SingleReadQuery<E> extends ReadQuery<E,E>{
	
	private final PersistenceGateway gateway;
	
	/**
	 * Constructor
	 * @param entityClass the entityClass
	 * @param em the entityManager
	 * @param builderFactory the builderFactory
	 * @param gateway the gateway
	 */
	public SingleReadQuery(Class<E> entityClass, EntityManager em,
			StatementBuilderFactory builderFactory,PersistenceGateway gateway) {
		super(entityClass, em, builderFactory);
		this.gateway = gateway;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.ReadQuery#doCallInternal(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	protected E doCallInternal(ReadingConditions conditions) {
		List<E> result = gateway.getResultList(conditions);
		return result.isEmpty() ? null : result.get(0);
	}

}
