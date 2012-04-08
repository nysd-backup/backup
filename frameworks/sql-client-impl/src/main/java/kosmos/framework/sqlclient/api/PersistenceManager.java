/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.List;

import javax.persistence.OptimisticLockException;

import kosmos.framework.sqlclient.api.exception.DeadLockException;
import kosmos.framework.sqlclient.api.exception.UniqueConstraintException;
import kosmos.framework.sqlclient.api.orm.PersistenceHints;


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
	public <T> void batchPersist(List<T> entity,PersistenceHints hints);

	/**
	 * Inserts the entity.
	 * @param entity the entity
	 * @param hints hints
	 * @return the updated count
	 * @throws DeadLockException throw when the update conflict occur. 
	 * @throws UniqueConstraintException throw when the record already exists.
	 */
	public void persist(Object entity,PersistenceHints hints);
	
	/**
	 * Updates the entity.
	 * @param entity the entity
	 * @param hints the hints
	 * @throws OptimisticLockException throw when the version no is not found.
	 * @throws DeadLockException throw when the update conflict occur. 
	 * @return the updated count
	 */
	public <T> void batchUpdate(List<T> entity,PersistenceHints hints);
	
	/**
	 * Updates the entity.
	 * @param entity the entity
	 * @param hints the hints
	 * @throws OptimisticLockException throw when the version no is not found.
	 * @throws DeadLockException throw when the update conflict occur. 
	 * @return the updated count
	 */
	public <T> void merge(T entity,T found,PersistenceHints hints);
	
	/**
	 * Deletes the entity.
	 * @param entity the entity
	 * @param hints the hints
	 * @return the updated count
	 * @throws OptimisticLockException throw when the version no is not found.
	 * @throws DeadLockException throw when the delete conflict occur. 
	 */
	public void remove(Object entity,PersistenceHints hints);
	
}
