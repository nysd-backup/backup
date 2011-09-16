/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.orm;

import framework.sqlclient.api.orm.OrmUpdate;


/**
 * JPQLのバルクアップデート用.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface JPAOrmUpdate<T> extends OrmUpdate<T>{
	
	/**
	 * @param <T> 型
	 * @param key　 ヒント句キー
	 * @param value　ヒント句
	 * @return self
	 */
	public JPAOrmUpdate<T> setHint(String key, Object value);
	
	/**
	 * @param condition 条件
	 * @return self
	 */
	public JPAOrmUpdate<T> setCondition(JPAOrmCondition<T> condition);

}

