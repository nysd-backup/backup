/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kosmos.framework.jpqlclient.EntityManagerProvider;


/**
 * Provides the <code>EntityManager</code>.
 * Requires the CDI.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
@ExcludeDefaultInterceptors
public class EntityManagerProviderImpl implements EntityManagerProvider{

	/** the <code>EntityManager</code> */
	@PersistenceContext(unitName="default")
	private EntityManager em;
		
	/**
	 * @see kosmos.framework.jpqlclient.EntityManagerProvider#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager(){	
		return em;
	}

}
