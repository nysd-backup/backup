/**
 * Copyright 2011 the original author
 */
package sqlengine.service;

import sqlengine.strategy.RecordFilter;

/**
 * The query parameter.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class ReadingRequest extends QueryRequest{
	
	/** マックス件数 */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/** the result type */
	private Class resultType = null;
	
	/** the filter for one record */
	private RecordFilter filter;	
	
	/** the fetchSize */
	private int fetchSize;
	
	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(RecordFilter filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter
	 */
	public RecordFilter getFilter() {
		return filter;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(Class resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the resultType
	 */
	public Class getResultType() {
		return resultType;
	}

	/**
	 * @param firstResult the firstResult to set
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * @return the fetchSize
	 */
	public int getFetchSize() {
		return fetchSize;
	}

	/**
	 * @param fetchSize the fetchSize to set
	 */
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	
}
