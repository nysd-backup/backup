/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Convert the result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface TypeConverter {
	
	/**
	 * @param cast the cast target
	 * @param resultSet the resultSet
	 * @param columnLabel the columnLabel
	 * @return the casted object
	 * @throws SQLException the exception
	 */
	Object getParameter(Class<?> cast , ResultSet resultSet , String columnLabel)throws SQLException ;
	
	/**
	 * @param arg
	 * @param value
	 * @param statement
	 * @throws SQLException
	 */
	void setParameter(int arg, Object value,PreparedStatement statement) throws SQLException;
}
