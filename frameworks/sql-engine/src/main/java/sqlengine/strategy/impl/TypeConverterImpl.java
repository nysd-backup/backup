/**
 * Copyright 2011 the original author
 */
package sqlengine.strategy.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import sqlengine.strategy.TypeConverter;



/**
 * Convert the result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class TypeConverterImpl implements TypeConverter{

	/**
	 * @see sqlengine.strategy.TypeConverter#getParameter(java.lang.Class, java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public Object getParameter(Class<?> type, ResultSet resultSet , String columnLabel) throws SQLException {
		if( String.class.equals(type)) {
			return resultSet.getString(columnLabel);
		}else if( BigDecimal.class.equals(type)){
			return resultSet.getBigDecimal(columnLabel);
		}else if( Long.class.equals(type) || long.class.equals(type)){
			return resultSet.getLong(columnLabel);
		}else if( Integer.class.equals(type) || int.class.equals(type)){
			return resultSet.getInt(columnLabel);				
		}else if( Short.class.equals(type) || short.class.equals(type)){
			return resultSet.getShort(columnLabel);	
		}else if( Byte.class.equals(type) || byte.class.equals(type)){
			return resultSet.getByte(columnLabel);		
		}else if( byte[].class.equals(type)){
			return resultSet.getBytes(columnLabel);		
		}else if(Date.class.equals(type)|| Timestamp.class.equals(type)|| Time.class.equals(type)){
			return new java.util.Date(resultSet.getTimestamp(columnLabel).getTime()); 		
		}else if( Boolean.class.equals(type) || boolean.class.equals(type)){
			return resultSet.getBoolean(columnLabel);
		}else if( Double.class.equals(type) || double.class.equals(type)) {
			return resultSet.getDouble(columnLabel);
		}else if( Float.class.equals(type) || float.class.equals(type)) {
			return resultSet.getFloat(columnLabel);
		}else if ( InputStream.class.equals(type) ){
			return resultSet.getBinaryStream(columnLabel);
		}else {
			return resultSet.getObject(columnLabel);
		}
	}

	/**
	 * @see sqlengine.strategy.TypeConverter#setParameter(int, java.lang.Object, java.sql.PreparedStatement)
	 */
	@Override
	public void setParameter(int arg, Object value,PreparedStatement statement) throws SQLException{
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
		}else if ( value instanceof byte[]){
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
		}else if ( value instanceof InputStream ){
			statement.setBinaryStream(arg, InputStream.class.cast(value));
		}else {		
			statement.setObject(arg, value);
		}
	
	}

}
