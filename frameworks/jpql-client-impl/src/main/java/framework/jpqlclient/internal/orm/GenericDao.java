/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.orm;

import java.util.List;
import java.util.Map;

import framework.jpqlclient.api.orm.JPAOrmCondition;

/**
 * The generic DAO.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface GenericDao {
	
	/**
	 *　Updates the table.
	 *
	 * @param condition the condition
	 * @param set the updating target
	 */
	public abstract int updateAny(JPAOrmCondition<?> condition , Map<String,Object> set);
	
	/** 
	 * Deletes the table.
	 * 
	 * @param condition the condition
	 */
	public abstract int deleteAny(JPAOrmCondition<?> condition);
	
	/**
	 * Finds by primary keys.
	 * 
	 * @param <E> the type
	 * @param entity the condition
	 * @param pks the primary keys
	 * @return the result
 	 */
	public <E> E find(JPAOrmCondition<E> entity , Object... pks);
	
	/**
	 * Searches the records.
	 * 
	 * @param <E> the type
	 * @param entity the condition
	 * @return the result
	 */
 	public <E> List<E> getResultList(JPAOrmCondition<E> entity);
 	
	/**
	 * Finds by alter keys.
	 * Throws the error if the multiple result is found.
	 * 
	 * @param <E> the type
	 * @param entity　the condition
	 * @return the result
	 */
	public <E> E findAny(JPAOrmCondition<E> entity);
	
}
