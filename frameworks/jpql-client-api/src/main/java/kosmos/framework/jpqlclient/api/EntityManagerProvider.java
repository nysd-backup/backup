/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api;

import javax.persistence.EntityManager;

/**
 * Provides the <code>EntityManager</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EntityManagerProvider {

	/**
	 * @return the entity manager
	 */
	public EntityManager getEntityManager();
}
