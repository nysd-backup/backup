/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.coder.alpha.jdbc.exception.QueryException;
import org.coder.alpha.jdbc.strategy.StatementProvider;
import org.coder.alpha.jdbc.strategy.TypeConverter;





/**
 * Provides the <code>Statement</code>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultStatementProvider implements StatementProvider{
	
	private TypeConverter converter = new DefaultTypeConverter();
	
	/** the resultSetType */
	private int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
	
	/** the resultSetConcurrency */
	private int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
	
	/**
	 * @param converter the converter to set
	 */
	public void setTypeConverter(TypeConverter converter){
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
	 * @see org.coder.alpha.jdbc.strategy.StatementProvider#createStatement(java.lang.String, java.sql.Connection, java.lang.String, int, int, int)
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

	/**
	 * @see org.coder.alpha.jdbc.strategy.StatementProvider#setParameter(java.sql.PreparedStatement, java.util.List)
	 */
	@Override
	public void setParameter(PreparedStatement stmt, List<Object> bindList) 
	throws SQLException{		
		for(int i = 0 ; i < bindList.size(); i ++){	
			converter.setParameter(i+1, bindList.get(i), stmt);
		}
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.StatementProvider#setBatchParameter(java.sql.PreparedStatement, java.util.List)
	 */
	@Override
	public void setBatchParameter(PreparedStatement stmt,
			List<List<Object>> bindList) throws SQLException{		
		for(List<Object> values : bindList){		
			for(int i = 0 ; i < values.size(); i ++){
				converter.setParameter(i+1, values.get(i), stmt);
			}
			stmt.addBatch();
		}
		
	}

}
