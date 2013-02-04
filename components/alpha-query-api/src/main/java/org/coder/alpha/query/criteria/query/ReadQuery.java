/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.coder.alpha.query.PersistenceHints;
import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.criteria.Metadata;
import org.coder.alpha.query.criteria.SortKey;
import org.coder.alpha.query.criteria.statement.JPQLBuilderFactory;
import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.free.ReadingConditions;
import org.coder.alpha.query.free.ResultSetFilter;

/**
 * ReadQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ReadQuery<E,T> extends CriteriaQuery<E,T>{
	
	/** resultClass */
	private final Class<E> entityClass;

	/** entity manager */
	private final EntityManager em;
	
	/** factory of statement builder */
	private StatementBuilderFactory builderFactory = new JPQLBuilderFactory();
	
	/** lock type */
	private LockModeType lockModeType = null;
			
	/** start position to search */
	private int firstResult = -1;
	
	/** persistence hints */
	private PersistenceHints hints = new PersistenceHints();
	
	/** filter of the result */
	private ResultSetFilter filter = null;
	
	/** sorting keys */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();
	
	/**
	 * @param entityClass
	 * @param em
	 * @param builderFactory
	 */
	public ReadQuery(Class<E> entityClass,EntityManager em){
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
	 * @param lockModeType
	 */
	public ReadQuery<E,T> setLockModeType(LockModeType lockModeType) {
		this.lockModeType = lockModeType;
		//ロック指定の場合はタイムアウト設定、先にタイムアウト設定されていた場合は何もしない
		if(hints.getLockTimeout() <= 0){
			if(LockModeType.OPTIMISTIC == lockModeType){
				hints.setLockTimeout(0);
			}
		}
		return this;
	}

	/**
	 * Set the hint values.
	 * @param key the key
	 * @param value the value
	 * @return self
	 */
	public ReadQuery<E,T> setHint(String key , Object value){
		hints.put(key, value);
		return this;
	}
	
	/**
	 * Set the start position
	 * @param firstResult the firstResult
	 * @return self
	 */
	public ReadQuery<E,T> setFirstResult(int firstResult){
		this.firstResult = firstResult;
		return this;
	}
	
	/**
	 * Set the query filter
	 * @param filter the filter
	 * @return self
	 */
	public ReadQuery<E,T> setFilter(ResultSetFilter filter){
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
	public ReadQuery<E,T> desc(Metadata<E,?> column){
		sortKeys.add(new SortKey(false,column.name()));
		return this;
	}
	
	/**
	 * Adds 'ASC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public ReadQuery<E,T> asc(Metadata<E, ?> column){
		sortKeys.add(new SortKey(true,column.name()));
		return this;
	}

	/**
	 * @see org.coder.alpha.query.criteria.query.CriteriaQuery#doCall(org.coder.alpha.query.criteria.statement.StatementBuilder)
	 */
	@Override
	protected T doCall(List<Criteria> criterias){
		ReadingConditions parameter = new ReadingConditions();		
		parameter.setEntityManager(em);
		parameter.setLockMode(lockModeType);
		parameter.setFilter(filter);
		parameter.setSql(builderFactory.createBuilder().withSelect(entityClass).withWhere(criterias).withOrderBy(sortKeys).withLock(lockModeType, hints.getLockTimeout()).build());
		parameter.setResultType(entityClass);
		parameter.setQueryId(entityClass+".select");
		for(Criteria criteria : criterias){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: hints.entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		parameter.setFirstResult(firstResult);		
		
		return doCallInternal(parameter);
	}
	
	/**
	 * Call query
	 * @param conditions conditions to set
	 * @return result
	 */
	protected abstract T doCallInternal(ReadingConditions conditions);
	
}
