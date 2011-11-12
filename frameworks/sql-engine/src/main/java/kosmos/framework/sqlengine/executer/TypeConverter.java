/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer;

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
	public Object getParameter(Class<?> cast , ResultSet resultSet , String columnLabel)throws SQLException ;
}
