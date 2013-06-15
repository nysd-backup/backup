/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

import javax.persistence.LockModeType;

import org.coder.alpha.jdbc.strategy.RecordFilter;

/**
 * The read-query parameter.
 * 
 * @author yoshida-n
 * @version	created.
 */
public class ReadingConditions extends Conditions{
	
	/** the resultType */
	private Class<?> resultType;
	
	/** the lock mode */
	private LockModeType lock = null;
	
	/** the filter for <code>ResultSet</code> */
	private RecordFilter filter = null;

	/** the max size */
	private int maxResults = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/**
	 * @return the maxResults
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * @param maxResults the maxResults to set
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
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
	public RecordFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(RecordFilter filter) {
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
