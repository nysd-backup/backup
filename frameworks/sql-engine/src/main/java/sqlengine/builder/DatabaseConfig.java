/**
 * Copyright 2011 the original author
 */
package sqlengine.builder;

/**
 * DatabaseConfig.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DatabaseConfig {

	private int queryTimeout = 0;
	
	private int maxRows = 0;
	
	private int fetchSize = 0;

	/**
	 * @return the queryTimeout
	 */
	public int getQueryTimeout() {
		return queryTimeout;
	}

	/**
	 * @param queryTimeout the queryTimeout to set
	 */
	public void setQueryTimeout(int queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	/**
	 * @return the maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * @param maxRows the maxRows to set
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
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
