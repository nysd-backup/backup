/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import framework.sqlclient.api.orm.OrmCondition;

/**
 * ORM更新.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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
	
}
