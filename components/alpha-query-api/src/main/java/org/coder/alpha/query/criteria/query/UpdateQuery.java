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
import org.coder.alpha.query.criteria.Metadata;
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
public class UpdateQuery<E> extends ModifyQuery<E>{
	
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
	public UpdateQuery(Class<E> entityClass,EntityManager em,PersistenceGateway gateway) {
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
	public <V> UpdateQuery<E> set(Metadata<E,V> column , V value){
		setValues.put(column.name(), value);
		return this;
	}
	
	/**
	 * Low level API for String-based object.
	 * @param column the column 
	 * @return self
	 */
	public UpdateQuery<E> set(String column , FixString value){
		setValues.put(column, value);
		return this;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.ModifyQuery#doCallInternal(org.coder.alpha.query.free.ModifyingConditions, java.util.List)
	 */
	@Override
	protected Integer doCallInternal(ModifyingConditions conditions,
			List<Criteria> criterias,Class<E> entityClass) {
		
		StatementBuilder builder  = builderFactory.createBuilder();
		String sql = builder.withUpdate(entityClass).withSet(setValues).withWhere(criterias).build();
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
