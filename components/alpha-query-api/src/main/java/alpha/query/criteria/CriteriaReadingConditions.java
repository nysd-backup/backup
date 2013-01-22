/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import alpha.query.PersistenceHints;
import alpha.query.criteria.builder.QueryBuilder;
import alpha.query.free.ReadingConditions;
import alpha.query.free.ResultSetFilter;





/**
 * The condition to execute SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CriteriaReadingConditions<T> extends CriteriaConditions<T>{

	/** the max size to be able to search */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;

	/** the keys of the sorting */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();

	/** the lock mode */
	private LockModeType lockModeType;
	
	/** the filter */
	private ResultSetFilter filter;

	/**
	 * @param lockModeType the lockModeType to set
	 */
	public void setLockModeType(LockModeType lockModeType) {
		this.lockModeType = lockModeType;
		//ロック指定の場合はタイムアウト設定、先にタイムアウト設定されていた場合は何もしない
		if(!getHints().containsKey(PersistenceHints.PESSIMISTIC_LOCK_TIMEOUT)){
			if(LockModeType.OPTIMISTIC == lockModeType){
				setHint(PersistenceHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
			}
		}
	}

	/**
	 * @return the lockModeType
	 */
	public LockModeType getLockModeType() {
		return lockModeType;
	}
	
	/**
	 * @param entityClass the entityClass
	 */
	public CriteriaReadingConditions(Class<T> entityClass){
		super(entityClass);
	}
	
	
	/**
	 * @return the sort key
	 */
	public List<SortKey> getSortKeys() {
		return sortKeys;
	}
	
	/**
	 * @param maxSize the maxSize
	 */
	public void setMaxSize(int maxSize){
		this.maxSize = maxSize;
	}
	
	/**
	 * @return the maxSize
	 */
	public int getMaxSize(){
		return this.maxSize;
	}
	
	/**
	 * @param firstResult the firstResult
	 */
	public void setFirstResult(int firstResult){
		this.firstResult = firstResult;
	}
	
	/**
	 * @return the firstResult
	 */
	public int getFirstResult(){
		return this.firstResult;
	}

	/**
	 * @return the filter
	 */
	public ResultSetFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(ResultSetFilter filter) {
		this.filter = filter;
	}

	/**
	 * Creates the update Statement
	 * @return query string
	 */
	public ReadingConditions buildSelect(QueryBuilder builder){
		int timeout = 0;
		if(getHints().containsKey(PersistenceHints.PESSIMISTIC_LOCK_TIMEOUT)){
			timeout = (Integer)getHints().get(PersistenceHints.PESSIMISTIC_LOCK_TIMEOUT);
		}
		String sql = builder.withSelect(getEntityClass()).withWhere(getConditions()).withOrderBy(getSortKeys()).withLock(getLockModeType(),timeout).build();
		return createReadingConditions(sql);
	}

	/**
	 * Creates the selecting parameter
	 * @param condition the condition
	 * @return the parameter
	 */
	private <E> ReadingConditions createReadingConditions(String sql){
		ReadingConditions parameter = new ReadingConditions();		
		parameter.setEntityManager(getEntityManager());
		parameter.setLockMode(getLockModeType());
		parameter.setFilter(getFilter());
		parameter.setSql(sql);
		parameter.setResultType(getEntityClass());
		parameter.setQueryId(getEntityClass().getName()+".select");
		for(Criteria<?> criteria :getConditions()){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: getHints().entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		parameter.setFirstResult(getFirstResult());			
		parameter.setMaxSize(getMaxSize());
		return parameter;
	}
	
}
