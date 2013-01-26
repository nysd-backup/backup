/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.service;

import java.sql.Connection;
import java.util.List;

import org.coder.alpha.jdbc.domain.TotalData;



/**
 * The facade of the SQLEngine
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface QueryService {

	/**
	 * Executes the COUNT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the hit count
	 */
	long executeCount(ReadingRequest param , Connection con);
	
	/**
	 * Executes the SELECT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	<T> List<T> executeQuery(ReadingRequest param , Connection con);
	
	/**
	 * Executes the SELECT and fetch the result. 
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	<T> List<T> executeFetch(ReadingRequest param , Connection con);
	
	/**
	 * Executes the SELECT and get the hit count.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the result
	 */
	TotalData executeTotalQuery(ReadingRequest param , Connection con);
	
	/**
	 * Executes the UPDATE/DELETE/INSERT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the updated count
	 */
	int executeUpsert(ModifyingRequest parameter ,Connection con);
	
	/**
	 * Executes the batch UPDATE/DELETE/INSERT.
	 * 
	 * @param <T> the type
	 * @param param the parameters
	 * @param con the connection
	 * @return the updated count
	 */
	int[] executeBatch(List<ModifyingRequest> parameter ,Connection con);
}
