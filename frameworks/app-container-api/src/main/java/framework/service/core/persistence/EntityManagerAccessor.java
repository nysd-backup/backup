/**
 * Copyright 2011 the original author
 */
package framework.service.core.persistence;

import framework.core.entity.AbstractEntity;

/**
 * A wrapper for <code>EntityManager</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EntityManagerAccessor {	

	/**
	 * @param <T> the type
	 * @param entity the entity
	 */
	public <T extends AbstractEntity> void detach(T entity);
	
	/**
	 * @param <T> the type
	 * @param entity the entity
	 */
	public <T extends AbstractEntity> void reflesh(T entity);


	/**
	 * @param <T> the type
	 * @param entity the entity
	 */
	public <T extends AbstractEntity> void persist(T entity);

	/**
	 * @param <T> the type
	 * @param entity the entity
	 */
	public <T extends AbstractEntity> void remove(T entity);

	/**
	 * @param handlers the handlers to be invoked if an exception was thrown
	 */
	public <T extends AbstractEntity> void flush(FlushHandler... handlers);

}
