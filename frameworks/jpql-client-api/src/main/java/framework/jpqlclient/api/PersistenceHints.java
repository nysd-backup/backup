/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api;

/**
 * JPQLのヒント(Vender非依存）.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceHints {

	/**
     * "javax.persistence.cacheRetrieveMode"
     * <p>Configures the behavior when data is retrieved by the find methods and 
     * by the execution of queries. The cacheRetrieveMode is ignored for the 
     * refresh method, which always causes data to be retrieved from the 
     * database and not the cache.
     */
    public static final String CACHE_RETRIEVE_MODE = "javax.persistence.cacheRetrieveMode";
    
    /**
     * "javax.persistence.cacheStoreMode"
     * <p> Configures the behavior when data is read from the database and when 
     * data is committed into the database.
     */
    public static final String CACHE_STORE_MODE = "javax.persistence.cacheStoreMode";

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
    
    /**
     * "javax.persistence.lock.scope"
     * <p> By default pessimistic lock applied to only the tables mapped to the object being locked.
     * <p> It could be extended to apply also to relation (join) tables (ManyToMany and OneToOne case),
     * and CollectionTables (ElementCollection case). 
     * Valid values are defined in PessimisticLockScope.
     * @see javax.persistence.PessimisticLockScope
     * @see javax.persistence.LockModeType
     */
    public static final String PESSIMISTIC_LOCK_SCOPE = "javax.persistence.lock.scope";
  
}
