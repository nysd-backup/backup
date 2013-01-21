/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.strategy;

import java.util.List;

import alpha.query.criteria.CriteriaModifyingConditions;
import alpha.query.criteria.CriteriaReadingConditions;




/**
 * Internal Query Object.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface DataMapper {

	/**
	 *　Updates the table.
	 *
	 * @param parameter the parameter
	 * @param set the updating target
	 * @return the updated count
	 */
	public int update(CriteriaModifyingConditions<?> parameter);
	
	/** 
	 * Deletes the table.
	 * 
	 * @param condition the condition
	 */
	public int delete(CriteriaModifyingConditions<?> parameter);
	
	/**
	 * Searches the records.
	 * 
	 * @param <E> the type
	 * @param parameter the parameter
	 * @return the result
	 */
 	public <E> List<E> getResultList(CriteriaReadingConditions<E> parameter);
 	
 	
 	/**
 	 * Execute callback after select.
 	 * 
 	 * @param parameter the parameter
 	 * @param callback the callback to execute
 	 * @return the result
 	 */
 	public <E> List<E> getFetchResult(CriteriaReadingConditions<E> parameter);
 
}
