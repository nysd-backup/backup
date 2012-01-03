/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query;

import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;

/**
 * The ORM updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface LimitedOrmUpdate<T> {
	
	/**
	 * @return the current parameter
	 */
	public OrmUpdateParameter<T> getCurrentParams();

	/**
	 * @param <T> the type
	 * @param <Q> the type
	 * @param condition the condition to set
	 */
	public <Q extends LimitedOrmUpdate<T>> Q setCondition(OrmUpdateParameter<T> condition);
	
	/**
	 * @param <T> the type
	 * @param key　 the key of the hint
	 * @param value　the hint value
	 * @return self
	 */
	public <Q extends LimitedOrmUpdate<T>> Q setHint(String key, Object value);

	
}
