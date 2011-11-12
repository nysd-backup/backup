/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.query.services;

import java.util.List;

import kosmos.framework.core.entity.AbstractEntity;
import kosmos.framework.sqlclient.api.orm.OrmCondition;


/**
 * The ORM query service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmQueryService<T extends AbstractEntity> {

	/**
	 * Finds by primary keys.
	 * 
	 * @param request the request
	 * @param pks　the primary keys
	 * @return the result
	 */
	public T find(OrmCondition<T> request,Object[] pks);

	/**
	 * Finds by alter keys.
	 * Throws the error if the multiple records is found.
	 * 
	 * @param request the request
	 * @return　the result
	 */
	public T findAny(OrmCondition<T> request);

	/**
	 * Searches the records.
	 * 
	 * @param request the request
	 * @return the result
	 */
	public List<T> getResultList(OrmCondition<T> request);
	
	/**
	 * Searches the first record.
	 *
 	 * @param request the request
	 * @return the result
	 */
	public T getSingleResult(OrmCondition<T> request);
	
	/**
	 * Determines whether the result is found.
	 * 
	 * @param request the request
	 * @return true:exists
	 */
	public boolean exists(OrmCondition<T> request);
	
	/**
	 * Determines whether the result searched by primary keys is found.
	 *
	 * @param request the request
	 * @param pks the primary keys
	 * @return true:exsists
	 */
	public boolean exists(OrmCondition<T> request,Object[] pks);
	
	/**
	 * Determines whether the result is found.
	 * Throws the error if the multiple records is found. 
	 * 
	 * @param request the request
	 * @return true:exsists
	 */
	public boolean existsByAny(OrmCondition<T> request);	

}
