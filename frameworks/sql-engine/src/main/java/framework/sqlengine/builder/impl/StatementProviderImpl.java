/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import framework.sqlengine.builder.StatementProvider;

/**
 * Provides the <code>Statement</code>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class StatementProviderImpl implements StatementProvider{
	
	/**
	 * @see framework.sqlengine.builder.StatementProvider#createStatement(java.sql.Connection, java.lang.String)
	 */
	@Override
	public PreparedStatement createStatement(Connection con, String sql)
			throws SQLException {
		PreparedStatement statement = null;
		try{
			statement = configure(con.prepareStatement(sql));
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
	 * @see framework.sqlengine.builder.StatementProvider#createStatement(java.sql.Connection, java.lang.String, java.util.List, java.lang.String)
	 */
	@Override
	public PreparedStatement createStatement(Connection con ,String sql,List<Object> bindList,String queryId) throws SQLException{
		PreparedStatement statement = createStatement(con,sql);		
		setBindParameter(statement,bindList);
		return statement;
	}
	
	/**
	 * Configures the JDBC parameters including the vendor-depending-parameters.
	 * 
	 * @param stmt the statement
	 * @return the statement
	 */
	protected PreparedStatement configure(PreparedStatement stmt) throws SQLException{
		return stmt;
	}
	
	/**
	 * Binds the parameter to statement.
	 * 
	 * @param statement the statement
	 * @param bind the binding value
	 */
	protected void setBindParameter(PreparedStatement statement , List<Object> bind ) throws SQLException{
		
		for(int i = 0 ; i < bind.size() ; i++){
			
			try{
				Object value = bind.get(i);				
				int arg = i+1;
				if( value == null){
					statement.setNull(arg, Types.OTHER);
				}else if ( value instanceof String){
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
				}else if ( value instanceof Date ){
					statement.setDate(arg, new java.sql.Date(((Date) value).getTime()));
				}else if ( value instanceof Timestamp ){
					statement.setTimestamp(arg, Timestamp.class.cast(value));
				}else if ( value instanceof Time ){
					statement.setTime(arg, Time.class.cast(value));
				}else if ( value instanceof Boolean ){
					statement.setBoolean(arg, Boolean.class.cast(value));
				}else if ( value instanceof Double ){
					statement.setDouble(arg, Double.class.cast(value));	
				}else if ( value instanceof Float ){
					statement.setDouble(arg, Float.class.cast(value));	
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
