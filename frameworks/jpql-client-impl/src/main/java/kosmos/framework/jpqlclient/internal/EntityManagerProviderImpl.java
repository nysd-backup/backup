/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;

/**
 * Provides the <code>EntityManager</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class EntityManagerProviderImpl implements EntityManagerProvider{

	/** the EntityManager */
	@PersistenceContext
	private EntityManager em;
		
	/**
	 * @see kosmos.framework.jpqlclient.api.EntityManagerProvider#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager(){	
		return em;
	}
}
