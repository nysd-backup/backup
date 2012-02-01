/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kosmos.framework.sqlengine.builder.StatementProvider;
import kosmos.framework.sqlengine.exception.SQLEngineException;
import kosmos.framework.sqlengine.executer.TypeConverter;
import kosmos.framework.sqlengine.executer.impl.TypeConverterImpl;


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
	
	/** the converter. */
	private TypeConverter converter = new TypeConverterImpl();
	
	/**
	 * @param converter the converter to set
	 */
	public void setConveter(TypeConverter converter){
		this.converter = converter;
	}

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
	public PreparedStatement createStatement(String sqlId ,Connection con, String sql, int timeout , int maxRows , int fetchSize){
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
			throw new SQLEngineException(sqle);
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
				converter.setParameter(arg, value, statement);
			}catch(SQLException sqle){
				statement.close();
				throw sqle;
			}
		}		
	}


}
