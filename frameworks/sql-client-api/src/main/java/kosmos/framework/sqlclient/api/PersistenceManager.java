/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.List;

import javax.persistence.OptimisticLockException;


/**
 * PersistenceManager.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface PersistenceManager {

	/**
	 * Inserts the entity.
	 * @param entity the entity
	 * @param hints hints
	 * @return the updated count
	 * @throws DeadLockException throw when the update conflict occur. 
	 * @throws UniqueConstraintException throw when the record already exists.
	 */
	public int insert(Object entity,PersistenceHints hints);

	/**
	 * For batch insert.
	 * 
	 * @param entity the entity
	 * @param hints hints
	 * @return the updated count
	 */
	public int[] batchInsert(List<?> entity,PersistenceHints hints);	
	
	/**
	 * Updates the entity.
	 * @param entity the entity
	 * @param hints the hints
	 * @throws OptimisticLockException throw when the version no is not found.
	 * @throws DeadLockException throw when the update conflict occur. 
	 * @return the updated count
	 */
	public int update(Object entity,PersistenceHints hints);
	
	/**
	 * Deletes the entity.
	 * @param entity the entity
	 * @param hints the hints
	 * @return the updated count
	 * @throws OptimisticLockException throw when the version no is not found.
	 * @throws DeadLockException throw when the delete conflict occur. 
	 */
	public int delete(Object entity,PersistenceHints hints);
}
