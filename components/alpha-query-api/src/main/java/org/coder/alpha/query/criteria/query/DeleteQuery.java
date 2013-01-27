/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.criteria.statement.StatementBuilder;
import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.free.ModifyingConditions;
import org.coder.alpha.query.free.gateway.PersistenceGateway;

/**
 * UpdateQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DeleteQuery<E> extends ModifyQuery<E>{
	
	/** the persistenceGateway */
	private PersistenceGateway gateway;
	
	/** the query builder factory */
	private StatementBuilderFactory builderFactory;
	
	/**
	 * Constructor.
	 * @param em the em to set
	 * @param entityClass the entityClass
	 * @param builderFactory the factory to set
	 * @param gateway the gateway to set
	 */
	public DeleteQuery(Class<E> entityClass,EntityManager em,StatementBuilderFactory builderFactory,PersistenceGateway gateway) {
		super(entityClass,em);
		this.builderFactory = builderFactory;
		this.gateway = gateway;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.ModifyQuery#doCallInternal(org.coder.alpha.query.free.ModifyingConditions, java.util.List)
	 */
	@Override
	protected Integer doCallInternal(ModifyingConditions conditions,
			List<Criteria> criterias,Class<E> entityClass) {
		StatementBuilder builder  = builderFactory.createBuilder();
		String sql = builder.withDelete(entityClass).withWhere(criterias).build();
		conditions.setQueryId(entityClass.getSimpleName() + ".delete");
		conditions.setSql(sql);		
		return gateway.executeUpdate(conditions);
	}

}
