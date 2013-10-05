/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.criteria.FixString;
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
public class UpdateQuery extends ModifyQuery{
	
	/** the persistenceGateway */
	private PersistenceGateway gateway;
	
	/** the query builder factory */
	private StatementBuilderFactory builderFactory;
	
	/** the updating target */
	private Map<String,Object> setValues = new HashMap<String,Object>();
	
	/**
	 * Constructor.
	 * @param em the em to set
	 * @param entityClass the entityClass
	 * @param gateway the gateway to set
	 */
	public UpdateQuery(Class<?> entityClass,EntityManager em,PersistenceGateway gateway) {
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
	 * Low level API for String-based object.
	 * @param column the column 
	 * @return self
	 */
	public UpdateQuery set(String column , FixString value){
		setValues.put(column, value);
		return this;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.ModifyQuery#doCallInternal(org.coder.alpha.query.free.ModifyingConditions, java.util.List)
	 */
	@Override
	protected Integer doCallInternal(Conditions conditions,
			List<Criteria> criterias,Class<?> entityClass) {
		
		StatementBuilder builder  = builderFactory.createBuilder();
		String sql = builder.withSet(setValues).withWhere(criterias).buildUpdate(entityClass);
		conditions.setQueryId(entityClass.getSimpleName() + ".update");
		conditions.setSql(sql);
		//set
		for(Map.Entry<String, Object> v: setValues.entrySet()){
			if(!(v.getValue() instanceof FixString)){
				conditions.getParam().put(v.getKey(),v.getValue());
			}
		}
		
		return gateway.executeUpdate(conditions);
	}

}
