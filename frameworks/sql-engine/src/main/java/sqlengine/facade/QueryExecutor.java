/**
 * Copyright 2011 the original author
 */
package sqlengine.facade;

import java.sql.Connection;
import java.util.List;

/**
 * The facade of the SQLEngine
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface QueryExecutor {

	/**
	 * Executes the COUNT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the hit count
	 */
	long executeCount(SelectParameter param , Connection con);
	
	/**
	 * Executes the SELECT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	<T> List<T> executeQuery(SelectParameter param , Connection con);
	
	/**
	 * Executes the SELECT and fetch the result. 
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	<T> List<T> executeFetch(SelectParameter param , Connection con);
	
	/**
	 * Executes the SELECT and get the hit count.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	QueryResult executeTotalQuery(SelectParameter param , Connection con);
	
	/**
	 * Executes the UPDATE/DELETE/INSERT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the updated count
	 */
	int executeUpsert(UpsertParameter parameter ,Connection con);
	
	/**
	 * Executes the batch UPDATE/DELETE/INSERT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the updated count
	 */
	int[] executeBatch(List<UpsertParameter> parameter ,Connection con);
}
