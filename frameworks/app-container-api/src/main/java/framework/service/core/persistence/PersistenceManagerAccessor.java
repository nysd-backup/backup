/**
. * Use is subject to license terms.
 */
package framework.service.core.persistence;

import framework.core.entity.AbstractEntity;

/**
 *  パーシステンスマネージャのアクセサ.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface PersistenceManagerAccessor {

	public <T extends AbstractEntity> T makePersistent(T entity);
	
	public <T extends AbstractEntity> void deletePersistent(T entity);
	
	public <T extends AbstractEntity> T detachCopy(T entity);
	
	public void flush(FlushHandler... flushHandlers);
}
