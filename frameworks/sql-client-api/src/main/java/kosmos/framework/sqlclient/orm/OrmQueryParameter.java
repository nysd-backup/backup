/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.PersistenceHints;


/**
 * The condition to execute SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryParameter<T> extends OrmParameter<T>{

	private static final long serialVersionUID = 1L;

	/** the max size to be able to search */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;

	/** the keys of the sorting */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();

	/** the lock mode */
	private LockModeType lockModeType;

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
	public OrmQueryParameter(Class<T> entityClass){
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

}
