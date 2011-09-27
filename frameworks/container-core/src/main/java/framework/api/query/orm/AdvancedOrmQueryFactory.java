/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import framework.core.entity.AbstractEntity;

/**
 * ORMクエリを生成する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface AdvancedOrmQueryFactory {

	/**
	 * StrictQueryを生成する。
	 * 
	 * @param <T>　型
	 * @param <Q> 　型
	 * @param entityClass エンティティクラス
	 */
	public <T extends AbstractEntity> StrictQuery<T> createStrictQuery(Class<T> entityClass);
	
	/**
	 * EasyQueryを生成する。
	 * 
	 * @param <T>　型
	 * @param <Q> 　型
	 * @param entityClass エンティティクラス
	 */
	public <T extends AbstractEntity> EasyQuery<T> createEasyQuery(Class<T> entityClass);
	
	/**
	 * StrictUpdateを生成する。
	 * 
	 * @param <T>　型
	 * @param <Q> 　型
	 * @param entityClass エンティティクラス
	 */
	public <T extends AbstractEntity> StrictUpdate<T> createStrictUpdate(Class<T> entityClass);

	/**
	 * EasyUpdateを生成する。
	 * 
	 * @param <T>　型
	 * @param <Q> 　型
	 * @param entityClass エンティティクラス
	 */
	public <T extends AbstractEntity> EasyUpdate<T> createEasyUpdate(Class<T> entityClass);

}
