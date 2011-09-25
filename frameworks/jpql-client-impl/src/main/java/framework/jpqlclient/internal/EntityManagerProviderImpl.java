/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.internal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import framework.jpqlclient.api.EntityManagerProvider;

/**
 * エンティティマネージャ提供者.
 *
 * @author yoshida-n
 * @version	2011/02/23 created.
 */
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
