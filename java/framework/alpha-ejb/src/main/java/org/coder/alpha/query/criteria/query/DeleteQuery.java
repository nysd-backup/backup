/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.criteria.statement.JPQLBuilderFactory;
import org.coder.alpha.query.criteria.statement.StatementBuilder;
import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.free.query.Conditions;
import org.coder.alpha.query.gateway.PersistenceGateway;

/**
 * UpdateQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DeleteQuery extends ModifyQuery{
	
	/** the persistenceGateway */
	private PersistenceGateway gateway;
	
	/** the query builder factory */
	private StatementBuilderFactory builderFactory = new JPQLBuilderFactory();
	
	/**
	 * Constructor.
	 * @param em the em to set
	 * @param entityClass the entityClass
	 * @param gateway the gateway to set
	 */
	public DeleteQuery(Class<?> entityClass,EntityManager em,PersistenceGateway gateway) {
		super(entityClass,em);
		this.gateway = gateway;
	}
	
	/**
	 * @param builderFactory the builderFactory to set
	 */
	public void setStatementBuilderFactory(StatementBuilderFactory builderFactory){
		this.builderFactory = builderFactory;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.ModifyQuery#doCallInternal(org.coder.alpha.query.free.ModifyingConditions, java.util.List)
	 */
	@Override
	protected Integer doCallInternal(Conditions conditions,
			List<Criteria> criterias,Class<?> entityClass) {
		StatementBuilder builder  = builderFactory.createBuilder();
		String sql = builder.withWhere(criterias).buildDelete(entityClass);
		conditions.setQueryId(entityClass.getSimpleName() + ".delete");
		conditions.setSql(sql);		
		return gateway.executeUpdate(conditions);
	}

}
