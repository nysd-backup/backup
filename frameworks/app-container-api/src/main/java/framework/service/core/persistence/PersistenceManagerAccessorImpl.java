/**
 * Use is subject to license terms.
 */
package framework.service.core.persistence;

import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import framework.core.entity.AbstractEntity;
import framework.jdoclient.api.PersistenceManagerProvider;

/**
 * パーシステンスマネージャのアクセサ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceManagerAccessorImpl implements PersistenceManagerAccessor{
	
	
	/** パーシステンスマネージャ */
	private PersistenceManager pm;

	/**
	 * 初期化処理
	 * @param パーシステンスマネージャ
	 */
	public void setEPersistenceManagerProvider(PersistenceManagerProvider provider){		
		pm = provider.getPersistenceManager();
	}

	/**
	 * @see framework.service.core.persistence.PersistenceManagerAccessor#makePersistent(framework.core.entity.AbstractEntity)
	 */
	@Override
	public <T extends AbstractEntity> T makePersistent(T entity) {
		return pm.makePersistent(entity);		
	}

	/**
	 * @see framework.service.core.persistence.PersistenceManagerAccessor#deletePersistence(framework.core.entity.AbstractEntity)
	 */
	@Override
	public <T extends AbstractEntity> void deletePersistent(T entity) {
			pm.deletePersistent(entity);
	}

	/**
	 * @see framework.service.core.persistence.PersistenceManagerAccessor#detachCopy(framework.core.entity.AbstractEntity)
	 */
	@Override
	public <T extends AbstractEntity> T detachCopy(T entity) {
		return pm.detachCopy(entity);
	}

	/**
	 * @see framework.service.core.persistence.PersistenceManagerAccessor#flush(framework.service.core.persistence.FlushHandler[])
	 */
	@Override
	public void flush(FlushHandler... handlers) {
		try{
			pm.flush();
		}catch(JDOException pe){
			if( handlers == null || handlers.length == 0){
				throw pe;
			}
			for(FlushHandler handler : handlers){			
				handler.handle(pe);
			}
		}
	}

}