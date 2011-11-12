/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;


/**
 * The adapter of SessionContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface SessionContextAdapter {

	/**
	 * Get the id of the current transaction from SessionContext.
	 * 
	 * @param context the context
	 * @return the id
	 */
	public String getCurrentTransactionId();

	/**
	 * Set rollback only to the SessionContext.
	 */
	public void setRollbackOnly();
}
