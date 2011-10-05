/**
 * Copyright 2011 the original author
 */
package framework.service.ext.define;

import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import framework.jpqlclient.api.EntityManagerProvider;

/**
 * Provides the <code>EntityManager</code>.
 * Requires the CDI.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Alternative
public class EntityManagerProviderImpl implements EntityManagerProvider{

	/** the <code>EntityManager</code> */
	@PersistenceContext
	private EntityManager em;
		
	/**
	 * @see framework.jpqlclient.api.EntityManagerProvider#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager(){	
		return em;
	}

}
