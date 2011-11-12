/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;


/**
 * The factory to create the query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmQueryFactory {
	
	/**
	 * Creates the query.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T,Q extends OrmQuery<T>> Q createQuery(Class<T> entityClass);
	
	/**
	 * Creates the updater.
	 * 
	 * @param <T>　the type
	 * @param entityClass the entityClass
	 * @return self
	 */
	public <T,Q extends OrmUpdate<T>> Q createUpdate(Class<T> entityClass);

}
