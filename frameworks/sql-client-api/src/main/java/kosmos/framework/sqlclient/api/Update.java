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
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public <T extends Update> T setHint(String arg0 , Object arg1);
	
	/**
	 * Updates the data.
	 * 
	 * @return the updated count
	 */
	public int update();
	
}
