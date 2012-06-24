/**
 * Copyright 2011 the original author
 */
package client.sql;

import javax.persistence.EntityManager;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class QueryParameter {

	private EntityManager entityManager;

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
