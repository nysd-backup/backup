/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.coder.alpha.query.PersistenceHints;
import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.free.ModifyingConditions;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ModifyQuery<E> extends CriteriaQuery<E,Integer>{

	private Class<E> entityClass;
	
	private final EntityManager em;

	private PersistenceHints hints = new PersistenceHints();
	
	/**
	 * @param entityClass
	 * @param em
	 */
	public ModifyQuery(Class<E> entityClass,EntityManager em){
		this.em = em;
		this.entityClass = entityClass;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public ModifyQuery<E> setHint(String key , Object value){
		hints.put(key, value);
		return this;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.CriteriaQuery#doCall(org.coder.alpha.query.criteria.statement.StatementBuilder)
	 */
	@Override
	protected Integer doCall(List<Criteria> criterias){
		ModifyingConditions parameter = new ModifyingConditions();		
		parameter.setEntityManager(em);
		for(Criteria criteria : criterias){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: hints.entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		return doCallInternal(parameter,criterias,entityClass);
	}
	
	/**
	 * @param conditions
	 * @return
	 */
	protected abstract Integer doCallInternal(ModifyingConditions conditions,List<Criteria> criterias,Class<E> entityClass);
	
}
