/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import framework.sqlclient.api.orm.OrmCondition;

/**
 * ORM更新.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface AdvancedOrmUpdate<T> {

	/**
	 * @param <T> 型
	 * @param <Q> 型
	 * @param condition 条件
	 */
	public <Q extends AdvancedOrmUpdate<T>> Q setCondition(OrmCondition<T> condition);
	
	/**
	 * @param <T> 型
	 * @param key　 ヒント句キー
	 * @param value　ヒント句
	 * @return self
	 */
	public <Q extends AdvancedOrmUpdate<T>> Q setHint(String key, Object value);
	
	/**
	 * SQLヒント句を設定する。
	 * @param <Q> 型
	 * @param hintValue ヒント句
	 * @return self
	 */
	public <Q extends AdvancedOrmUpdate<T>> Q setHintString(String hintValue);
}
