/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.orm;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.orm.OrmQuery;


/**
 * The ORM query for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface JPAOrmQuery<T> extends OrmQuery<T>{
	
	/**
	 * @param <T> the type
	 * @param key　 the key of the hint
	 * @param value　the hint value
	 * @return self
	 */
	public abstract JPAOrmQuery<T> setHint(String key, Object value);

	/**
	 * @param type the lock mode
	 * @return self
	 */
	public JPAOrmQuery<T> setLockMode(LockModeType type);
	
	/**
	 * @param condition 条件
	 * @return self
	 */
	public JPAOrmQuery<T> setCondition(JPAOrmCondition<T> condition);
}

