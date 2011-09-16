/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import javax.persistence.LockModeType;

import framework.sqlclient.api.orm.OrmCondition;


/**
 * OrmQuery用API.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface AdvancedOrmQuery<T> {
	
	/**
	 * @param <T> 型
	 * @param key　 ヒント句キー
	 * @param value　ヒント句
	 * @return self
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q setHint(String key, Object value);

	/**
	 * LockModeTypeを指定する.
	 * @param lockModeType ロックモード
	 * @return self
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q setLockMode(LockModeType lockModeType);
	
	/**
	 * 悲観ロック取得
	 * @param lockModeType ロックモード
	 * @return self
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q setPessimiticLock(int timeout);
	
	/**
	 * 悲観ロック取得
	 * @param lockModeType ロックモード
	 * @return self
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q setPessimiticLockNoWait();

	/**
	 * @param condition 条件
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q setCondition(OrmCondition<T> condition);
	
	/**
	 * 主キー検索
	 * @param pks　主キー
	 * @return 検索結果
	 */
	public abstract T find(Object... pks);

	/**
	 * 主キー検索
	 * @param pks　主キー
	 * @return 検索結果
	 */
	public abstract T findWithLockNoWait(Object... pks);

	/**
	 * 主キー指定存在チェック
	 * @param pks 主キー
	 * @return true:存在する
	 */
	public abstract boolean exists(Object... pks);

	/**
	 * @return 0件時システムエラー
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q enableNoDataError();

	/**
	 * @param arg0 最大件数
	 * @return self
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q setMaxResults(int arg0);

	/**
	 * @param arg0 先頭位置
	 * @return self
	 */
	public abstract <Q extends AdvancedOrmQuery<T>> Q setFirstResult(int arg0);


}
