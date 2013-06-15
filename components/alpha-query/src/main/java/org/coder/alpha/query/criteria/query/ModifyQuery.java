/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.free.Conditions;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ModifyQuery<E> extends CriteriaQuery<E,Integer>{

	private Class<E> entityClass;
	
	private final EntityManager em;

	/**
	 * @param entityClass
	 * @param em
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
	 * @param conditions
	 * @return
	 */
	protected abstract Integer doCallInternal(Conditions conditions,List<Criteria> criterias,Class<E> entityClass);
	
}
