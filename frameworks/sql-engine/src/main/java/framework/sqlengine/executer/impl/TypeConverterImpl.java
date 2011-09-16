/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import framework.sqlengine.executer.TypeConverter;

/**
 * 型変換エンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
public class TypeConverterImpl implements TypeConverter{

	/**
	 * @see framework.sqlengine.executer.TypeConverter#getParameter(java.lang.Class, java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public Object getParameter(Class<?> type, ResultSet resultSet , String columnLabel) throws SQLException {
		if(Date.class.equals(type)|| Timestamp.class.equals(type)|| Time.class.equals(type)){
			return new Date(resultSet.getTimestamp(columnLabel).getTime()); 		
		}else if( String.class.equals(type)) {
			return resultSet.getString(columnLabel);
		}else if( BigDecimal.class.equals(type)){
			return resultSet.getBigDecimal(columnLabel);
		}else if( Integer.class.equals(type) || int.class.equals(type)){
			return resultSet.getInt(columnLabel);				
		}else if( Short.class.equals(type) || short.class.equals(type)){
			return resultSet.getShort(columnLabel);	
		}else if( Byte.class.equals(type) || byte.class.equals(type)){
			return resultSet.getByte(columnLabel);		
		}else if( byte[].class.equals(type)){
			return resultSet.getBytes(columnLabel);
		}else if( Long.class.equals(type) || long.class.equals(type)){
			return resultSet.getLong(columnLabel);
		}else if( Boolean.class.equals(type) || boolean.class.equals(type)){
			return resultSet.getBoolean(columnLabel);
		}else if( Double.class.equals(type) || double.class.equals(type)) {
			return resultSet.getDouble(columnLabel);
		}else if( Float.class.equals(type) || float.class.equals(type)) {
			return resultSet.getFloat(columnLabel);
		}else if (Object.class.equals(type)){
			return resultSet.getObject(columnLabel);
		}else {
			throw new IllegalArgumentException(String.format("invalid type : %s " ,type.getName()));
		}
	}

}
