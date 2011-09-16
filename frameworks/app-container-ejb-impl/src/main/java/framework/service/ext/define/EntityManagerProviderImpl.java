/**
 * Use is subject to license terms.
 */
package framework.service.ext.define;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import framework.jpqlclient.api.EntityManagerProvider;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class EntityManagerProviderImpl implements EntityManagerProvider{

	/** エンティティマネージャ */
	@PersistenceContext
	private EntityManager em;
		
	/**
	 * @see framework.jpqlclient.internal.em.EntityManagerProvider#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager(){	
		return em;
	}

}
