/**
 * Use is subject to license terms.
 */
package framework.service.core.persistence;

import framework.core.entity.AbstractEntity;

/**
 * エンティティマネージャのラッパー.
 *
 * @author yoshida-n
 * @version	2011/04/13 created.
 */
public interface EntityManagerAccessor {	

	/**
	 * @param <T> 型
	 * @param entity エンティティ
	 */
	public <T extends AbstractEntity> void detach(T entity);
	
	/**
	 * @param <T> 型
	 * @param entity エンティティ
	 */
	public <T extends AbstractEntity> void reflesh(T entity);


	/**
	 * @param <T> 型
	 * @param entity エンティティ
	 */
	public <T extends AbstractEntity> void persist(T entity);

	/**
	 * @param <T> 型
	 * @param entity エンティティ
	 */
	public <T extends AbstractEntity> void remove(T entity);

	/**
	 * @param handlers フラッシュハンドラ
	 */
	public <T extends AbstractEntity> void flush(FlushHandler... handlers);

}
