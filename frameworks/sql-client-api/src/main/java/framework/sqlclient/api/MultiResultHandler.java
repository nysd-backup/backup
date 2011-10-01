/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api;


/**
 * Handles the multiple result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface MultiResultHandler {

	/**
	 * @param condition the condition
	 */
	public void handleResult(Object condition);
}
