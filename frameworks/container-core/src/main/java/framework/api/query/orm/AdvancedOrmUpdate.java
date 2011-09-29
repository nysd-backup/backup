/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import framework.sqlclient.api.orm.OrmCondition;

/**
 * The ORM updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface AdvancedOrmUpdate<T> {

	/**
	 * @param <T> the type
	 * @param <Q> the type
	 * @param condition the condition to set
	 */
	public <Q extends AdvancedOrmUpdate<T>> Q setCondition(OrmCondition<T> condition);
	
	/**
	 * @param <T> the type
	 * @param key　 the key of the hint
	 * @param value　the hint value
	 * @return self
	 */
	public <Q extends AdvancedOrmUpdate<T>> Q setHint(String key, Object value);
	
}
