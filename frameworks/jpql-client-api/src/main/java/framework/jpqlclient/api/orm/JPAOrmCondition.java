/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.orm;

import javax.persistence.LockModeType;

import framework.sqlclient.api.orm.OrmCondition;

/**
 * JPA用検索条件.
 *
 * @author yoshida-n
 * @version	created.
 */
public class JPAOrmCondition<T> extends OrmCondition<T>{

	private static final long serialVersionUID = 1L;

	/** ロックモード */
	private LockModeType lockModeType;
	
	/**
	 * @param entityClass エンティティクラス
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
