/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.facade;

import java.sql.Connection;
import java.util.List;

/**
 * The facade of the SQLEngine
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface SQLEngineFacade {

	/**
	 * Executes the COUNT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the hit count
	 */
	public int executeCount(QueryParameter param , Connection con);
	
	/**
	 * Executes the SELECT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	public <T> List<T> executeQuery(QueryParameter param , Connection con);
	
	/**
	 * Executes the SELECT and fetch the result. 
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	public <T> List<T> executeFetch(QueryParameter param , Connection con);
	
	/**
	 * Executes the SELECT and get the hit count.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	public QueryResult executeTotalQuery(QueryParameter param , Connection con);
	
	/**
	 * Executes the UPDATE/DELETE/INSERT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the updated count
	 */
	public int executeUpdate(UpdateParameter parameter ,Connection con);
}
