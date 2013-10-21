/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.SortKey;
import org.coder.gear.query.criteria.statement.JPQLBuilderFactory;
import org.coder.gear.query.criteria.statement.StatementBuilderFactory;
import org.coder.gear.query.free.RecordFilter;
import org.coder.gear.query.free.query.ReadingConditions;
import org.eclipse.persistence.config.QueryHints;

/**
 * ReadQuery.
 *
 * @author yoshida-n
 * @version	1.0
 */
public abstract class ReadQuery<T> extends CriteriaQuery<T>{
	
	/** resultClass */
	private final Class<?> entityClass;

	/** entity manager */
	private final EntityManager em;
	
	/** factory of statement builder */
	private StatementBuilderFactory builderFactory = new JPQLBuilderFactory();
	
	/** lock type */
	private LockModeType lockModeType = null;
			
	/** start position to search */
	private int offset = -1;
		
	/** filter of the result */
	private RecordFilter filter = null;
	
	/** sorting keys */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();
	
	/**
	 * Constuctor.
	 * 
	 * @param entityClass the entity class
	 * @param em the entity manager
	 */
	public ReadQuery(Class<?> entityClass,EntityManager em){
		this.entityClass = entityClass;
		this.em = em;
	}
	
	/**
	 * @param builderFactory the builderFactory to set
	 */
	public void setStatementBuilderFactory(StatementBuilderFactory builderFactory){
		this.builderFactory = builderFactory;
	}
	
	/**
	 * Set lock mode type.
	 * 
	 * @param lockModeType
	 * @return self
	 */
	public ReadQuery<T> lock(LockModeType lockModeType) {
		this.lockModeType = lockModeType;
		//ロック指定の場合はタイムアウト設定、先にタイムアウト設定されていた場合は何もしない
		if(getLockTimeout() <= 0){
			if(LockModeType.OPTIMISTIC == lockModeType){
				setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
			}
		}
		return this;
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
	 * Set the query filter.
	 * @param filter the filter
	 * @return self
	 */
	public ReadQuery<T> filter(RecordFilter filter){
		this.filter = filter;
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
		ReadingConditions parameter = new ReadingConditions();		
		parameter.setEntityManager(em);
		parameter.setLockMode(lockModeType);
		parameter.setFilter(filter);
		parameter.setSql(builderFactory.createBuilder().withWhere(criterias).withOrderBy(sortKeys).buildSelect(entityClass));
		parameter.setResultType(entityClass);
		parameter.setQueryId(entityClass+".select");
		for(Criteria criteria : criterias){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: getHints().entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		parameter.setFirstResult(offset);		
		
		return doCallInternal(parameter);
	}
	
	/**
	 * get the timeout.
	 * 
	 * @return the timeout
	 */
	private int getLockTimeout(){
		Map<String,Object> hints = getHints();
		Object v = hints.get(QueryHints.PESSIMISTIC_LOCK_TIMEOUT);
		return v == null ? 0 : (Integer)v;
	}
	
	/**
	 * Call query
	 * @param conditions conditions to set
	 * @return result
	 */
	protected abstract T doCallInternal(ReadingConditions conditions);
	
}
