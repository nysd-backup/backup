/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.orm;

import javax.persistence.LockModeType;

import framework.sqlclient.api.orm.OrmQuery;

/**
 * ORマッピングクエリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface JPAOrmQuery<T> extends OrmQuery<T>{
	
	/**
	 * @param <T> 型
	 * @param key　 ヒント句キー
	 * @param value　ヒント句
	 * @return self
	 */
	public abstract JPAOrmQuery<T> setHint(String key, Object value);

	/**
	 * @param type ロックモード
	 * @return self
	 */
	public JPAOrmQuery<T> setLockMode(LockModeType type);
	
	/**
	 * @param condition 条件
	 * @return self
	 */
	public JPAOrmQuery<T> setCondition(JPAOrmCondition<T> condition);
}

