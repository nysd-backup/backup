/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.query.orm;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.orm.OrmCondition;



/**
 *　The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface AdvancedOrmQuery<T> {
	
	/**
	 * Enables pessimistic read.
	 * 
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setPessimisticReadNoWait();
	
	/**
	 * @param <T> the type
	 * @param key　 the key of the hint
	 * @param value　the hint value
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setHint(String key, Object value);

	/**
	 * @param lockModeType the lockModeType to set
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setLockMode(LockModeType lockModeType);

	/**
	 * @param condition the condition to set
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setCondition(OrmCondition<T> condition);
	
	/**
	 * Finds by primary key.
	 * @param pks　the primary keys
	 * @return the result
	 */
	public T find(Object... pks);

	/**
	 * Determines whether the result searched by primary keys is found.
	 * @param pks the primary keys
	 * @return true:exists
	 */
	public boolean exists(Object... pks);

	/**
	 * Throws error if no result is found.
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q enableNoDataError();

	/**
	 * @param arg0 the max results
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setMaxResults(int arg0);

	/**
	 * @param arg0 the start position
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setFirstResult(int arg0);

}
