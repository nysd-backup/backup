/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.free.query.Conditions;

/**
 * ModifyQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ModifyQuery<E> extends CriteriaQuery<E,Integer>{

	/** entity class. */
	private Class<E> entityClass;
	
	/** entity manager. */
	private final EntityManager em;

	/**
	 * Constructor.
	 * 
	 * @param entityClass the entityClass
	 * @param em the entity manager
	 */
	public ModifyQuery(Class<E> entityClass,EntityManager em){
		this.em = em;
		this.entityClass = entityClass;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.CriteriaQuery#doCall(org.coder.alpha.query.criteria.statement.StatementBuilder)
	 */
	@Override
	protected Integer doCall(List<Criteria> criterias){
		Conditions parameter = new Conditions();		
		parameter.setEntityManager(em);
		for(Criteria criteria : criterias){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: getHints().entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		return doCallInternal(parameter,criterias,entityClass);
	}
	
	/**
	 * Calls query.
	 * 
	 * @param conditions the conditions
	 * @param criterias the criteria
	 * @param entityClass the entityClass
	 * @return the result
	 */
	protected abstract Integer doCallInternal(Conditions conditions,List<Criteria> criterias,Class<E> entityClass);
	
}
