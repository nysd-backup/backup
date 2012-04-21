/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm.strategy;

import java.util.List;

import kosmos.framework.sqlclient.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.orm.OrmUpdateParameter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface InternalOrmQuery {

	/**
	 *　Updates the table.
	 *
	 * @param parameter the parameter
	 * @param set the updating target
	 * @return the updated count
	 */
	public int update(OrmUpdateParameter<?> parameter);
	
	/** 
	 * Deletes the table.
	 * 
	 * @param condition the condition
	 */
	public int delete(OrmUpdateParameter<?> parameter);
	
	/**
	 * Finds by primary keys.
	 * 
	 * @param <E> the type
	 * @param parameter the parameter
	 * @param pks the primary keys
	 * @return the result
 	 */
	public <E> E find(OrmQueryParameter<E> parameter,Object... pks);
	
	/**
	 * Searches the records.
	 * 
	 * @param <E> the type
	 * @param parameter the parameter
	 * @return the result
	 */
 	public <E> List<E> getResultList(OrmQueryParameter<E> parameter);
 	
 	
 	/**
 	 * Execute callback after select.
 	 * 
 	 * @param parameter the parameter
 	 * @param callback the callback to execute
 	 * @return the result
 	 */
 	public <E> List<E> getFetchResult(OrmQueryParameter<E> parameter);
 
}
