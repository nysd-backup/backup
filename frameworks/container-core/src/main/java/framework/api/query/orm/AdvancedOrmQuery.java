/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import javax.persistence.LockModeType;

import framework.sqlclient.api.orm.OrmCondition;


/**
 *　ORMクエリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface AdvancedOrmQuery<T> {
	
	/**
	 * SQLヒント句を設定する。
	 * @param <Q> 型
	 * @param hintValue ヒント句
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setHintString(String hintValue);
	
	/**
	 * DBから直接検索し永続化コンテキストを最新の値で更新する。
	 * @param <Q> 型
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setRefleshMode();
	
	/**
	 * @param <T> 型
	 * @param key　 ヒント句キー
	 * @param value　ヒント句
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setHint(String key, Object value);


	/**
	 * LockModeTypeを指定する.
	 * @param lockModeType ロックモード
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setLockMode(LockModeType lockModeType);

	/**
	 * 悲観ロック設定.
	 * 
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setPessimisticRead();

	/**
	 * @param condition 条件
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setCondition(OrmCondition<T> condition);
	
	/**
	 * 主キー検索
	 * @param pks　主キー
	 * @return 検索結果
	 */
	public T find(Object... pks);

	/**
	 * 主キー指定存在チェック
	 * @param pks 主キー
	 * @return true:存在する
	 */
	public boolean exists(Object... pks);

	/**
	 * @return 0件時システムエラー
	 */
	public <Q extends AdvancedOrmQuery<T>> Q enableNoDataError();

	/**
	 * @param arg0 最大件数
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setMaxResults(int arg0);

	/**
	 * @param arg0 先頭位置
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setFirstResult(int arg0);

}
