/**
 * Copyright 2011 the original author
 */
package client.sql.orm.strategy;

import java.util.List;

import client.sql.orm.CriteriaReadQueryParameter;
import client.sql.orm.CriteriaModifyQueryParameter;



/**
 * Internal Query Object.
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
	public int update(CriteriaModifyQueryParameter<?> parameter);
	
	/** 
	 * Deletes the table.
	 * 
	 * @param condition the condition
	 */
	public int delete(CriteriaModifyQueryParameter<?> parameter);
	
	/**
	 * Searches the records.
	 * 
	 * @param <E> the type
	 * @param parameter the parameter
	 * @return the result
	 */
 	public <E> List<E> getResultList(CriteriaReadQueryParameter<E> parameter);
 	
 	
 	/**
 	 * Execute callback after select.
 	 * 
 	 * @param parameter the parameter
 	 * @param callback the callback to execute
 	 * @return the result
 	 */
 	public <E> List<E> getFetchResult(CriteriaReadQueryParameter<E> parameter);
 
}
