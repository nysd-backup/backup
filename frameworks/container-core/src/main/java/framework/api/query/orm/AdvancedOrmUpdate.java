/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import framework.sqlclient.api.orm.OrmCondition;

/**
 * ORMUpdateの基底.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface AdvancedOrmUpdate<T> {

	/**
	 * @param condition 条件
	 */
	public abstract <Q extends AbstractAdvancedOrmUpdate<T>> Q setCondition(OrmCondition<T> condition);
	
	/**
	 * @param <T> 型
	 * @param key　 ヒント句キー
	 * @param value　ヒント句
	 * @return self
	 */
	public abstract <Q extends AbstractAdvancedOrmUpdate<T>> Q setHint(String key, Object value);
}
