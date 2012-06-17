/**
 * Copyright 2011 the original author
 */
package core.message;

/**
 * Reserved messages for framework to use.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface MessageId {
	
	/** the other error */
	public static final String OTHER_ERROR				 	= "other.error";
	
	/** the database exception */
	public static final String PERSISTENCE_ERROR		 	= "persistence.error";
	
	/** unexpected no data found */
	public static final String UNEXPECTED_NODATA_ERROR 		= "unexpected.nodata.error";
	
	/** unexpected data found */
	public static final String UNEXPECTED_DATAFOUND_ERROR 	= "unexpected.datafound.error";
	
	/** optimistic lock error */
	public static final String OPTIMISTIC_LOCK_ERROR 		= "optimistic.lock.error";
	
	/** pessimistic lock error */
	public static final String RESOURCE_BUSY_ERROR			= "resource.busy.error";
	
	/** dead lock error */
	public static final String DEAD_LOCK_ERROR				= "dead.lock.error";
	
	/** illegal for unique constraint */
	public static final String UNIQUE_CONSTRAINT_ERROR		= "unique.constraint.error";
	
	/** lock timed out */
	public static final String LOCK_TIMEOUT_ERROR		= "lock.timeout.error";
	
	/** jdbc query timed out */
	public static final String JDBC_TIMEOUT_ERROR		= "jdbc.timeout.error";

}
