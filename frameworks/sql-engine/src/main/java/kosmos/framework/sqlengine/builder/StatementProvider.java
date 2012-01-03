/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder;

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
	 * For {@link PreparedStatement#executeBatch()}.
	 * 
	 * @param sqlId the queryId
	 * @param con the connection
	 * @param sql the SQL
	 * @return the statement
	 */
	public PreparedStatement createStatement(String sqlId,Connection con ,String sql ,int timeout , int maxRows,int fetchSize) throws SQLException;
	
	/**
	 * Creates the statement.
	 * For {@link PreparedStatement#executeBatch()}.
	 * 
	 * @param sqlId the queryId
	 * @param con the connection
	 * @param sql the SQL
	 * @return the statement
	 */
	public PreparedStatement buildStatement(String sqlId,Connection con ,String sql ,List<Object> bindList, int timeout , int maxRows,int fetchSize) throws SQLException;
	
	/**
	 * Binds the parameter to statement.
	 * 
	 * @param statement the statement
	 * @param bind the binding value
	 */
	public void setBindParameter(PreparedStatement statement , List<Object> bind ) throws SQLException;

}
