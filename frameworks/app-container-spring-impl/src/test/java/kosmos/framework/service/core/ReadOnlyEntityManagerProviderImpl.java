/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;

/**
 * Provides the <code>EntityManager</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ReadOnlyEntityManagerProviderImpl implements EntityManagerProvider{

	/** the EntityManager */
	@PersistenceContext(unitName="readOnlyOracle")
	private EntityManager em;
		
	/**
	 * @see kosmos.framework.jpqlclient.api.EntityManagerProvider#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager(){	
		return em;
	}
}
