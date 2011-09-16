/**
 * Use is subject to license terms.
 */
package framework.service.core.persistence;

import framework.core.entity.AbstractEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/04/13 created.
 */
public interface EntityManagerAccessor {	

	/**
	 * @param <T>
	 * @param entity
	 */
	public <T extends AbstractEntity> void detach(T entity);
	
	/**
	 * @param <T>
	 * @param entity
	 */
	public <T extends AbstractEntity> void reflesh(T entity);


	/**
	 * @param <T>
	 * @param entity
	 */
	public <T extends AbstractEntity> void persist(T entity);

	/**
	 * @param <T>
	 * @param entity
	 */
	public <T extends AbstractEntity> void remove(T entity);

	/**
	 * @param <T>
	 * @param handlers
	 */
	public <T extends AbstractEntity> void flush(FlushHandler... handlers);

}
