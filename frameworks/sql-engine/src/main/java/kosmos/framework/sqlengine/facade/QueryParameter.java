/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.facade;

import kosmos.framework.sqlengine.executer.RecordFilter;

/**
 * The query parameter.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryParameter<T> extends SQLParameter{
	
	/** マックス件数 */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/** the result type */
	private Class<T> resultType = null;
	
	/** the filter for one record */
	private RecordFilter<T> filter;	
	
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
	public void setFilter(RecordFilter<T> filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter
	 */
	public RecordFilter<T> getFilter() {
		return filter;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(Class<T> resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the resultType
	 */
	public Class<T> getResultType() {
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

	
}