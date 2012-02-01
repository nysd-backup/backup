/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm;

import java.util.List;

import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface InternalOrmQuery {
	
	/**
	 * Updates the batch.
	 * 
	 * @param parameter the parameter to set
	 * @return the inserted count
	 */
	public int[] batchUpdate(OrmUpdateParameter<?> parameter);
	
	/**
	 * Updates the batch.
	 * 
	 * @param parameter the parameter to set
	 * @return the inserted count
	 */
	public int[] batchInsert(OrmUpdateParameter<?> parameter);
	
	/**
	 * Insert the record.
	 * 
	 * @param parameter the parameter
	 * @param hints the hints
	 * @return the inserted count
	 */
	public int insert(OrmUpdateParameter<?> parameter);
	
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
 	public <E> List<E> getFetchResult(OrmQueryParameter<E> parameter , QueryCallback<E> callback);
 
}
