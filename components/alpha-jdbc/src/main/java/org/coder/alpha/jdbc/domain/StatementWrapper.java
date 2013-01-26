/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.coder.alpha.jdbc.exception.QueryException;
import org.coder.alpha.jdbc.strategy.Selector;
import org.coder.alpha.jdbc.strategy.StatementProvider;
import org.coder.alpha.jdbc.strategy.Updater;



/**
 * Wrapper for the prepared statement.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StatementWrapper {
	
	/** the statement provider */
	private StatementProvider provider;
	
	/** the PreparedStatement */
	private PreparedStatement statement;
	
	/** the binding tareget */
	private final List<List<Object>> bindList;
	
	/**
	 * @param statement the statement to set
	 * @param bindList the bindList to set
	 * @param provider the provider to set
	 */
	public StatementWrapper(PreparedStatement statement,List<List<Object>> bindList,StatementProvider provider){
		this.statement = statement;
		this.bindList = bindList;
		this.provider = provider;
	}
	
	/**
	 * Configures the statement
	 * @param maxRows the maxRows
	 * @param fetchSize the fetchSie
	 * @param timeout the timeout
	 * @throws SQLException
	 */
	public void configure(int maxRows, int fetchSize, int timeout)
			throws SQLException{
		if(maxRows > 0){
			statement.setMaxRows(maxRows);
		}
		if(fetchSize > 0){
			statement.setFetchSize(fetchSize);
		}
		if(timeout > 0){
			statement.setQueryTimeout(timeout);
		}
	}
	
	/**
	 * Select the data.
	 * @param selector the selector
	 * @return the result
	 * @throws SQLException
	 */
	public ResultSetWrapper read(Selector selector) throws SQLException{
		provider.setParameter(statement, bindList.get(0));
		return selector.select(statement);
	}
	
	/**
	 * Modify the table.
	 * @param updater the updater
	 * @return the updated count
	 * @throws SQLException
	 */
	public int modify(Updater updater) throws SQLException {
		provider.setParameter(statement, bindList.get(0));
		return updater.update(statement);
	}
	
	/**
	 * Modify the table.
	 * @param updater the updater
	 * @return the updated count (deprecated)
	 * @throws SQLException
	 */
	public int[] batchModify(Updater updater) throws SQLException {
		provider.setBatchParameter(statement, bindList);
		return updater.batchUpdate(statement);
	}
	
	/**
	 * Close the statement.
	 */
	public void close(){
		try{
			statement.close();
		}catch(SQLException sqle){
			throw new QueryException(sqle);
		}
	}

}
