/**
 * Use is subject to license terms.
 */
package framework.service.core.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import framework.core.entity.AbstractEntity;
import framework.jpqlclient.api.EntityManagerProvider;

/**
 * エンティティマネージャのラッパー.
 *
 * @author yoshida-n
 * @version	2011/04/13 created.
 */
public class EntityManagerAccessorImpl implements EntityManagerAccessor{
	
	/** エンティティマネージャ */
	private EntityManager em;

	/**
	 * @param provider the provider to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){		
		em = provider.getEntityManager();
	}

	/**
	 * @see framework.service.core.persistence.EntityManagerAccessor#persist(framework.core.entity.AbstractEntity)
	 */
	@Override
	public <T extends AbstractEntity> void persist(T entity){
		em.persist(entity);
	}
	
	/**
	 * @see framework.service.core.persistence.EntityManagerAccessor#remove(framework.core.entity.AbstractEntity)
	 */
	@Override
	public <T extends AbstractEntity> void remove(T entity){
		em.remove(entity);
	}
	
	/**
	 * @see framework.service.core.persistence.EntityManagerAccessor#flush(framework.service.core.persistence.FlushHandler[])
	 */
	@Override
	public <T extends AbstractEntity> void flush(FlushHandler... handlers){
	
		try{
			em.flush();
		}catch(PersistenceException pe){
			if( handlers == null || handlers.length == 0){
				throw pe;
			}
			for(FlushHandler handler : handlers){			
				handler.handle(pe);
			}
		}
	}

	/**
	 * @see framework.service.core.persistence.EntityManagerAccessor#detach(framework.core.entity.AbstractEntity)
	 */
	@Override
	public <T extends AbstractEntity> void detach(T entity) {
		em.detach(entity);
	}

	/**
	 * @see framework.service.core.persistence.EntityManagerAccessor#reflesh(framework.core.entity.AbstractEntity)
	 */
	@Override
	public <T extends AbstractEntity> void reflesh(T entity) {
		em.refresh(entity);
	}

}
