/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The query engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Selector {
	
	/**
	 * @param stmt　the statement that is binded the parameter.
	 * @return the result
	 * @throws SQLException the exception
	 */
	public ResultSet select(PreparedStatement stmt) throws SQLException;
}