/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.SortKey;
import org.coder.gear.query.free.query.Conditions;
import org.eclipse.persistence.config.QueryHints;

/**
 * ReadQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public abstract class ReadQuery<T> extends CriteriaQuery<T>{

	/** lock type */
	private LockModeType lockModeType = null;
			
	/** start position to search */
	private int offset = -1;
	
	/** sorting keys */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();
	
	/**
	 * Set lock mode type.
	 * 
	 * @param timeout timeout
	 * @return self
	 */
	public ReadQuery<T> forUpdate(int timeout) {
		this.lockModeType = LockModeType.PESSIMISTIC_READ;
		setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,timeout);
		return this;
	}

	/**
	 * Set lock mode type.
	 * 
	 * @return self
	 */
	public ReadQuery<T> forUpdateNoWait() {
		return forUpdate(0);
	}
	
	/**
	 * Set the start position.
	 * @param firstResult the firstResult
	 * @return self
	 */
	public ReadQuery<T> offset(int offset){
		this.offset = offset;
		return this;
	}

	/**
	 * Adds 'DESC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public ReadQuery<T> desc(String column){
		sortKeys.add(new SortKey(false,column));
		return this;
	}
	
	/**
	 * Adds 'ASC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public ReadQuery<T> asc(String column){
		sortKeys.add(new SortKey(true,column));
		return this;
	}

	/**
	 * @see org.coder.gear.query.criteria.query.CriteriaQuery#doCall(org.coder.gear.query.criteria.statement.StatementBuilder)
	 */
	@Override
	protected T doCall(List<Criteria> criterias){
		return doCallInternal(createConditions(criterias));
	}
	
	/**
	 * @param criterias to set
	 * @return condition 
	 */
	@SuppressWarnings("unchecked")
	protected Conditions createConditions(List<Criteria> criterias) {
		
		ParameterizedType t = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = t.getActualTypeArguments();				
		Class<?>  entityClass = (Class<T>)types[0];

		Conditions parameter = new Conditions();		
		parameter.setLockMode(lockModeType);
		parameter.setSql(builder.withWhere(criterias).withOrderBy(sortKeys).buildSelect(entityClass));
		parameter.setResultType(entityClass);
		parameter.setQueryId(entityClass+".select");
		for(Criteria criteria : criterias){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: getHints().entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		parameter.setFirstResult(offset);		
		return parameter;
	}
	
	/**
	 * Call query
	 * @param conditions conditions to set
	 * @return result
	 */
	protected abstract T doCallInternal(Conditions conditions);
	
}
