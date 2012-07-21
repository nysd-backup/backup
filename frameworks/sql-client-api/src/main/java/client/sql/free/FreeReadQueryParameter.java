/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import javax.persistence.LockModeType;

/**
 * The query parameter.
 * 
 * @author yoshida-n
 * @version	created.
 */
public class FreeReadQueryParameter extends FreeQueryParameter{
	
	/** the resultType */
	private Class<?> resultType;
	
	/** the lock mode */
	private LockModeType lock = null;
	
	/** the filter for <code>ResultSet</code> */
	private ResultSetFilter filter = null;

	/** the max size */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * @param firstResult the firstResult to set
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
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
	 * @return the lock
	 */
	public LockModeType getLockMode() {
		return lock;
	}

	/**
	 * @param lock the lock to set
	 */
	public void setLockMode(LockModeType lock) {
		this.lock = lock;
	}

	/**
	 * @return the resultType
	 */
	public Class<?> getResultType() {
		return resultType;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(Class<?> resultType) {
		this.resultType = resultType;
	}
	
}
