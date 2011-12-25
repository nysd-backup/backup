/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm;

import java.util.List;
import java.util.Map;

import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface InternalOrmQuery {
	
	
	/**
	 * Insert the record.
	 * 
	 * @param entity the entity
	 * @param hints the hints
	 * @return the inserted count
	 */
	public int insert(OrmContext<?> context , Map<String,Object> values);
	
	/**
	 *　Updates the table.
	 *
	 * @param condition the condition
	 * @param set the updating target
	 * @return the updated count
	 */
	public int update(OrmContext<?> context , Map<String,Object> set);
	
	/** 
	 * Deletes the table.
	 * 
	 * @param condition the condition
	 */
	public int delete(OrmContext<?> context);
	
	/**
	 * Finds by primary keys.
	 * 
	 * @param <E> the type
	 * @param entity the condition
	 * @param pks the primary keys
	 * @return the result
 	 */
	public <E> E find(OrmQueryContext<E> context,Object... pks);
	
	/**
	 * Searches the records.
	 * 
	 * @param <E> the type
	 * @param entity the condition
	 * @return the result
	 */
 	public <E> List<E> getResultList(OrmQueryContext<E> context);
 
}
