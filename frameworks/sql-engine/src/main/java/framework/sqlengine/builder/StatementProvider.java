/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides the <code>Statement</code>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StatementProvider {
	
	/**
	 * Creates the statement.
	 * 
	 * @param con the connection
	 * @param sql the SQL
	 * @param parameter the parameter
	 * @param queryId the queryId
	 * @return the statement
	 */
	public PreparedStatement createStatement(Connection con ,String sql , List<Object> parameter, String queryId) throws SQLException;

}
