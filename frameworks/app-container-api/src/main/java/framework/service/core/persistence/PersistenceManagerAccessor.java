/**
 * Copyright 2011 the original author
 */
package framework.service.core.persistence;

import framework.core.entity.AbstractEntity;

/**
 *  パーシステンスマネージャのラッパー.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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
