/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;


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
	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> query);
	

	/**
	 * Creates the updater.
	 *
	 * @param <T>　the type
	 * @param query the class of the query
	 * @return the query
	 */
	public <K extends FreeUpdate,T extends AbstractUpdate<K>> T createUpdate(Class<T> query);
	
}
