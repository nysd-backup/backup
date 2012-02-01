/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import javax.persistence.LockModeType;

/**
 * The query parameter.
 * 
 * @author yoshida-n
 * @version	created.
 */
public class FreeQueryParameter extends FreeParameter{
	
	/** the resultType */
	private final Class<?> resultType;
	
	/** the lock mode */
	private LockModeType lock = null;
	
	/** the filter for <code>ResultSet</code> */
	private ResultSetFilter filter = null;

	/** the max size */
	private int maxSize = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/**
	 * @param useRowSql the useRowSql
	 * @param queryId the queryId
	 * @param sql the SQL
	 */
	public FreeQueryParameter(Class<?> resultType,boolean useRowSql, String queryId, String sql) {
		super(useRowSql, queryId, sql);
		this.resultType = resultType;
	}
	
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
	 * @return the resultType
	 */
	public Class<?> getResultType() {
		return resultType;
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
	
}
