/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.orm;

import kosmos.framework.sqlclient.api.orm.OrmUpdate;


/**
 * The ORM updater for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface JPAOrmUpdate<T> extends OrmUpdate<T>{
	
	/**
	 * @param <T> the type
	 * @param key　 the key of the hint
	 * @param value　the hint value
	 * @return self
	 */
	public JPAOrmUpdate<T> setHint(String key, Object value);
	
	/**
	 * @param condition the condition
	 * @return self
	 */
	public JPAOrmUpdate<T> setCondition(JPAOrmCondition<T> condition);

}

