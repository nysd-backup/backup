/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.orm;

import javax.persistence.LockModeType;

import framework.sqlclient.api.orm.OrmCondition;

/**
 * The condition of JPA .
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class JPAOrmCondition<T> extends OrmCondition<T>{

	private static final long serialVersionUID = 1L;

	/** the lock mode */
	private LockModeType lockModeType;
	
	/**
	 * @param entityClass the entityClass to set
	 */
	public JPAOrmCondition(Class<T> entityClass) {
		super(entityClass);
	}

	/**
	 * @param lockModeType the lockModeType to set
	 */
	public void setLockModeType(LockModeType lockModeType) {
		this.lockModeType = lockModeType;
	}

	/**
	 * @return the lockModeType
	 */
	public LockModeType getLockModeType() {
		return lockModeType;
	}
	
}
