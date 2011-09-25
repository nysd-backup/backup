/**
 * Use is subject to license terms.
 */
package framework.service.core.persistence;

import framework.core.entity.AbstractEntity;

/**
 *  パーシステンスマネージャのラッパー.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface PersistenceManagerAccessor {

	/**
	 * @param <T> 型
	 * @param entity エンティティ
	 * @return エンティティ
	 */
	public <T extends AbstractEntity> T makePersistent(T entity);
	
	/**
	 * @param <T> 型
	 * @param entity エンティティ
	 */
	public <T extends AbstractEntity> void deletePersistent(T entity);
	
	/**
	 * @param <T> 型
	 * @param entity エンティティ
	 * @return エンティティ
	 */
	public <T extends AbstractEntity> T detachCopy(T entity);
	
	/**
	 * @param flushHandlers　フラッシュハンドラ
	 */
	public void flush(FlushHandler... flushHandlers);
}
