/**
 * Copyright 2011 the original author
 */
package framework.service.core.persistence;

/**
 * A aandler for failed process.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface FlushHandler {

	/**
	 * Handles the process if <code>PersistenceException</code> was thrown.
	 *  
	 * ex) refresh the context after an exception was thrown so as not to fail next 'em.flush'
	 * 
	 * @param pe　the exception
	 */
	public void handle(RuntimeException pe);
}
