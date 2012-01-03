/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import kosmos.framework.sqlengine.builder.StatementProvider;


/**
 * Provides the <code>Statement</code>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class StatementProviderImpl implements StatementProvider{
	
	/** the resultSetType */
	private int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
	
	/** the resultSetConcurrency */
	private int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;

	/**
	 * @param resultSetType the resultSetType to set
	 */
	public void setResultSetType(int resultSetType){
		this.resultSetType = resultSetType;
	}
	
	/**
	 * @param resultSetConcurrency the resultSetConcurrency to set
	 */
	public void setResultSetConcurrency(int resultSetConcurrency){
		this.resultSetConcurrency = resultSetConcurrency;
	}
	

	/**
	 * @param sqlId
	 * @param con
	 * @param sql
	 * @param bindList
	 * @param timeout
	 * @param maxRows
	 * @param fetchSize
	 * @return
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement buildStatement(String sqlId, Connection con,
			String sql, List<Object> bindList, int timeout, int maxRows,
			int fetchSize) throws SQLException {
		PreparedStatement stmt = createStatement(sqlId, con, sql, timeout, maxRows, fetchSize);
		setBindParameter(stmt, bindList);
		return stmt;
	}

	
	/**
	 * @see kosmos.framework.sqlengine.builder.StatementProvider#createStatement(java.lang.String, java.sql.Connection, java.lang.String, int, int, int)
	 */
	@Override
	public PreparedStatement createStatement(String sqlId ,Connection con, String sql, int timeout , int maxRows , int fetchSize)
			throws SQLException {
		PreparedStatement statement = null;
		try{
			statement = con.prepareStatement(sql,resultSetType,resultSetConcurrency);
			configure(statement,timeout,maxRows,fetchSize);
		}catch(SQLException sqle){
			if( statement != null){
				try{
					statement.close();
				}catch(SQLException s){					
				}
			}
			throw sqle;
		}
		return statement;
	}

	/**
	 * Configures the statement.
	 * 
	 * @param stmt the statement
	 * @param timeoutSeconds the timeout seconds
	 * @param maxRows the max rows
	 * @param fetchSize the fetchSize
	 * @throws SQLException the exception
	 */
	protected void configure(PreparedStatement stmt, int timeoutSeconds, int maxRows , int fetchSize) throws SQLException{
		if(timeoutSeconds > 0 ){
			stmt.setQueryTimeout(timeoutSeconds);
		}
		if(maxRows > 0 ){
			stmt.setMaxRows(maxRows);
		}
		if(fetchSize > 0){
			stmt.setFetchSize(fetchSize);
		}
	}

	/**
	 * @see kosmos.framework.sqlengine.builder.StatementProvider#setBindParameter(java.sql.PreparedStatement, java.util.List)
	 */
	@Override
	public void setBindParameter(PreparedStatement statement , List<Object> bind ) throws SQLException{
		
		for(int i = 0 ; i < bind.size() ; i++){
			try{
				Object value = bind.get(i);				
				int arg = i+1;
				if ( value instanceof String){
					statement.setString(arg, String.class.cast(value));
				}else if ( value instanceof Integer ){
					statement.setInt(arg, Integer.class.cast(value));
				}else if ( value instanceof Long ){
					statement.setLong(arg, Long.class.cast(value));
				}else if ( value instanceof Byte ){
					statement.setByte(arg, Byte.class.cast(value));	
				}else if ( value instanceof Short ){
					statement.setShort(arg, Short.class.cast(value));		
				}else if ( value instanceof byte[] ){
					statement.setBytes(arg, byte[].class.cast(value));			
				}else if ( value instanceof BigDecimal){
					statement.setBigDecimal(arg, BigDecimal.class.cast(value));
				}else if ( value instanceof java.sql.Date ){
					statement.setDate(arg, java.sql.Date.class.cast(value));
				}else if ( value instanceof Timestamp){
					statement.setTimestamp(arg, Timestamp.class.cast(value));					
				}else if ( value instanceof Time ){	
					statement.setTime(arg, Time.class.cast(value));
				}else if ( value instanceof java.util.Date){
					statement.setTimestamp(arg, new Timestamp(java.util.Date.class.cast(value).getTime()));	
				}else if ( value instanceof Boolean ){
					statement.setBoolean(arg, Boolean.class.cast(value));
				}else if ( value instanceof Double ){
					statement.setDouble(arg, Double.class.cast(value));	
				}else if ( value instanceof Float ){
					statement.setFloat(arg, Float.class.cast(value));	
				}else {
					statement.setObject(arg, value);
				}
			
			}catch(SQLException sqle){
				statement.close();
				throw sqle;
			}
		}		
	}


}
