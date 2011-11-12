/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;


/**
 * The base of the queries.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Update {
	
	/**
	 * Updates the data.
	 * 
	 * @return the updated count
	 */
	public int update();
	
}
