/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import framework.core.entity.AbstractEntity;

/**
 * ORMクエリを生成する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface AdvancedOrmQueryFactory {

	/**
	 * @param <T>
	 * @param <Q>
	 * @param entityClass
	 */
	public <T extends AbstractEntity> StrictQuery<T> createStrictQuery(Class<T> entityClass);
	
	/**
	 * @param <T>
	 * @param <Q>
	 * @param entityClass
	 */
	public <T extends AbstractEntity> EasyQuery<T> createEasyQuery(Class<T> entityClass);
	
	/**
	 * @param <T>
	 * @param <Q>
	 * @param entityClass
	 */
	public <T extends AbstractEntity> StrictUpdate<T> createStrictUpdate(Class<T> entityClass);

	/**
	 * @param <T>
	 * @param <Q>
	 * @param entityClass
	 */
	public <T extends AbstractEntity> EasyUpdate<T> createEasyUpdate(Class<T> entityClass);

}
