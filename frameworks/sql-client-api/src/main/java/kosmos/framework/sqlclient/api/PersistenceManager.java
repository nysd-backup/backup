/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface PersistenceManager {

	/**
	 * @param entity
	 * @return
	 */
	public int insert(Object entity) ;
	
	/**
	 * @param entity
	 * @return
	 */
	public int[] insert(Object[] entity) ;
	
	/**
	 * @param entity
	 * @param hints
	 * @return
	 */
	public int[] insert(Object[] entity,PersistenceHints hints);
	
	/**
	 * @param entity
	 * @param hints
	 * @return
	 */
	public int insert(Object entity,PersistenceHints hints);
	
	/**
	 * @param entity
	 * @param findedEntity
	 * @throws OptimisticLockException 
	 * @return
	 */
	public <T> int update(T entity, T findedEntity) ;
	
	/**
	 * @param entity
	 * @param findedEntity
	 * @param hints
	 * @throws OptimisticLockException 
	 * @return
	 */
	public <T> int update(T entity,T findedEntity,PersistenceHints hints);
	
	/**
	 * @param entity
	 * @return
	 */
	public int delete(Object entity);
	
	/**
	 * @param entity
	 * @param hints
	 * @return
	 */
	public int delete(Object entity,PersistenceHints hints);
}
