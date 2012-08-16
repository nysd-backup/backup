/**
 * Copyright 2011 the original author
 */
package sqlengine.builder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sqlengine.builder.StatementProvider;
import sqlengine.exception.QueryException;



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
	 * @see sqlengine.builder.StatementProvider#createStatement(java.lang.String, java.sql.Connection, java.lang.String, int, int, int)
	 */
	@Override
	public PreparedStatement createStatement(String sqlId ,Connection con, String sql){
		PreparedStatement statement = null;
		try{
			statement = con.prepareStatement(sql,resultSetType,resultSetConcurrency);			
		}catch(SQLException sqle){		
			throw new QueryException(sqle);
		}
		return statement;
	}

}
