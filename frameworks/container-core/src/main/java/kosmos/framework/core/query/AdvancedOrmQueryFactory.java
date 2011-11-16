/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query;

import kosmos.framework.core.entity.AbstractEntity;

/**
 * The factory to create ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface AdvancedOrmQueryFactory {

	/**
	 * Creates the <code>StrictQuery</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T extends AbstractEntity> StrictQuery<T> createStrictQuery(Class<T> entityClass);
	
	/**
	 * Creates the <code>EasyQuery</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T extends AbstractEntity> EasyQuery<T> createEasyQuery(Class<T> entityClass);
	
	/**
	 * Creates the <code>StrictUpdate</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T extends AbstractEntity> StrictUpdate<T> createStrictUpdate(Class<T> entityClass);

	/**
	 * Creates the <code>EasyUpdate</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T extends AbstractEntity> EasyUpdate<T> createEasyUpdate(Class<T> entityClass);

}
