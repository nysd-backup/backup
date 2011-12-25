/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;


/**
 * The condition to execute SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryContext<T> extends OrmContext<T>{

	private static final long serialVersionUID = 1L;

	/** the max size to be able to search */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;

	/** the keys of the sorting */
	private List<SortKey> sortKeys = new ArrayList<SortKey>();
	
	/** the order to sort */
	private String orderString = null;
	
	/** the lock mode */
	private LockModeType lockModeType;

	/**
	 * @param lockModeType the lockModeType to set
	 */
	public void setLockModeType(LockModeType lockModeType) {
		this.lockModeType = lockModeType;
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
	public OrmQueryContext(Class<T> entityClass){
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
	 * @param orderString the orderString to set
	 */
	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	/**
	 * @return the orderString
	 */
	public String getOrderString() {
		return orderString;
	}
}
