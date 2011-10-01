/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api;


/**
 * Handles the empty result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EmptyHandler {

	/**
	 * @param condition the condition
	 */
	public void handleEmptyResult(Object condition);
}
