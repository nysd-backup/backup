/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.HashMap;

/**
 * The hint of JPQL.
 *
 * @author yoshida-n
 * @version	2011/08/31 created.
 */
public class PersistenceHints extends HashMap<String,Object>{

	private static final long serialVersionUID = 1L;

    /**
     * "javax.persistence.lock.timeout"
     * <p>Configures the WAIT timeout used in pessimistic locking, if the database 
     * query exceeds the timeout the database will terminate the query and 
     * return an exception. Valid values are Integer or Strings that can be 
     * parsed to int values.
     * Some database platforms may not support lock timeouts, you may consider
     * setting a JDBC_TIMEOUT hint for these platforms.
     */
    public static final String PESSIMISTIC_LOCK_TIMEOUT = "javax.persistence.lock.timeout";
    
    /** the jdbc timeout for 'SQLEngine'*/
	public static final String SQLENGINE_JDBC_TIMEOUT = "kosmos.framework.sqlengine.jdbc.timeout";

	/** the jdbc fetch size for 'SQLEngine' */
	public static final String SQLENGINE_JDBC_FETCHSIZE = "kosmos.framework.sqlengine.jdbc.fetchsize";
	
	/** compare the column to select the updating target */
	public static final String COMPARE_FOUND_ENTITY = "kosmos.framework.sqlclient.compare.entity";
	
	/** the null column which is updatable  */
	public static final String INCLUDABLE_NULL_ITEMS = "kosmos.framework.sqlclient.includable.null.column";
	
	/**
	 * Sets the nullable column.
	 * @param the column
	 * @return self
	 */
	public PersistenceHints setIncludables(String... names){
		put(INCLUDABLE_NULL_ITEMS,names);
		return this;
	}
	
	/**
	 * @return includables
	 */
	public String[] getIncludables(){
		return (String[])get(INCLUDABLE_NULL_ITEMS);
	}
	
	/**
	 * Sets the lock timeout.
	 * @param timeout the timeout to set
	 * @return self
	 */
	public PersistenceHints setLockTimeout(int timeout){
		put(PESSIMISTIC_LOCK_TIMEOUT,timeout);
		return this;
	}
	
	/**
	 * @return lock timeout
	 */
	public int getLockTimeout(){
		return (Integer)get(PESSIMISTIC_LOCK_TIMEOUT);
	}
	
	/**
	 * Sets the found entity.
	 * @param foundEntity
	 * @return self
	 */
	public PersistenceHints setFoundEntity(Object foundEntity) {
		put(COMPARE_FOUND_ENTITY,foundEntity);
		return this;
	}
	
	/**
	 * @return the found entity.
	 */
	public Object getFoundEntity() {
		return get(COMPARE_FOUND_ENTITY);
	}
	
	/**
	 * Sets the jdbc timeout.
	 * @param timeout 
	 * @return self
	 */
	public PersistenceHints setJdbcTimeout(int timeout) {
		put(SQLENGINE_JDBC_TIMEOUT,timeout);
		return this;
	}
	
	/**
	 * @return the time out
	 */
	public int getJdbcTimeout() {
		return (Integer)get(SQLENGINE_JDBC_TIMEOUT);
	}
	
	/**
	 * Sets the fetch size.
	 * @param fetchSize
	 * @return self
	 */
	public PersistenceHints setJdbcFetchsize(int fetchSize) {
		put(SQLENGINE_JDBC_FETCHSIZE,fetchSize);
		return this;
	}
	
	/**
	 * @return the fetching size.
	 */
	public int getJdbcFetchsize() {
		return (Integer)get(SQLENGINE_JDBC_FETCHSIZE);
	}
  
}
