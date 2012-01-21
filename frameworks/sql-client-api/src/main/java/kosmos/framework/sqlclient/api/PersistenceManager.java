/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.List;

import javax.persistence.OptimisticLockException;


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
	public int[] insert(List<Object> entity) ;
	
	/**
	 * @param entity
	 * @param hints
	 * @return
	 */
	public int[] insert(List<Object> entity,PersistenceHints hints);
	
	/**
	 * @param entity
	 * @param hints
	 * @return
	 */
	public int insert(Object entity,PersistenceHints hints);
	
	/**
	 * @param entity
	 * @throws OptimisticLockException 
	 * @return
	 */
	public int update(Object entity) ;
	
	/**
	 * @param entity
	 * @param hints
	 * @throws OptimisticLockException 
	 * @return
	 */
	public int update(Object entity,PersistenceHints hints);
	
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
