/**
 * Copyright 2011 the original author
 */
package sqlengine.builder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sqlengine.builder.DatabaseConfig;
import sqlengine.builder.StatementProvider;
import sqlengine.exception.QueryException;
import sqlengine.executer.TypeConverter;
import sqlengine.executer.impl.TypeConverterImpl;



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
			String sql, List<Object> bindList, DatabaseConfig config) throws SQLException {
		PreparedStatement stmt = createStatement(sqlId, con, sql, config);
		setBindParameter(stmt, bindList);
		return stmt;
	}

	
	/**
	 * @see sqlengine.builder.StatementProvider#createStatement(java.lang.String, java.sql.Connection, java.lang.String, int, int, int)
	 */
	@Override
	public PreparedStatement createStatement(String sqlId ,Connection con, String sql, DatabaseConfig config){
		PreparedStatement statement = null;
		try{
			statement = con.prepareStatement(sql,resultSetType,resultSetConcurrency);
			configure(statement,config);
		}catch(SQLException sqle){
			if( statement != null){
				try{
					statement.close();
				}catch(SQLException s){					
				}
			}
			throw new QueryException(sqle);
		}
		return statement;
	}

	/**
	 * Configures the statement.
	 * 
	 * @param stmt the statement
	 * @param config the database configuration
	 * @throws SQLException the exception
	 */
	protected void configure(PreparedStatement stmt,DatabaseConfig config) throws SQLException{
		if(config.getQueryTimeout() > 0 ){
			stmt.setQueryTimeout(config.getQueryTimeout());
		}
		if(config.getMaxRows() > 0 ){
			stmt.setMaxRows(config.getMaxRows());
		}
		if(config.getFetchSize() > 0){
			stmt.setFetchSize(config.getFetchSize());
		}
	}

	/**
	 * @see sqlengine.builder.StatementProvider#setBindParameter(java.sql.PreparedStatement, java.util.List)
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
