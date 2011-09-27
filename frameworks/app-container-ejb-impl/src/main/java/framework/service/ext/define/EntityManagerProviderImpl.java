/**
 * Copyright 2011 the original author
 */
package framework.service.ext.define;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import framework.jpqlclient.api.EntityManagerProvider;

/**
 * エンティティマネージャの提供者.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class EntityManagerProviderImpl implements EntityManagerProvider{

	/** エンティティマネージャ */
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
