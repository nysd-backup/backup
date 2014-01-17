package org.coder.gear.sample.javaee7.domain.repository;

import org.coder.gear.sample.javaee7.domain.entity.AbstractEntity;

/**
 * @author yoshida-n
 *
 */
public interface Repository<T extends AbstractEntity> {
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public T find(Object pk);
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public boolean exists(Object pk);
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public boolean isEmpty(Object pk);
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public void remove(T entity);
	
	/**
	 * @param entityList
	 */
	@SuppressWarnings("unchecked")
	public void persist(T... entityList);

}
