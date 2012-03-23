/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;


/**
 * The factor to create query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface QueryFactory {

	/**
	 * Creates the query.
	 *
	 * @param <T>　the type
	 * @param query the class of the query
	 * @return the query
	 */
	<K extends FreeQuery> K createQuery();
	

	/**
	 * Creates the updater.
	 *
	 * @param <T>　the type
	 * @param query the class of the query
	 * @return the query
	 */
	<K extends FreeUpdate> K createUpdate();
	
}
