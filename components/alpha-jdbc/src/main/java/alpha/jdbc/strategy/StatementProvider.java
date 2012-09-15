/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy;

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
	PreparedStatement createStatement(String sqlId,Connection con ,String sql);
	
	void setParameter(PreparedStatement stmt, List<Object> bindList)throws SQLException;
	
	void setBatchParameter(PreparedStatement stmt, List<List<Object>> bindList)throws SQLException;

}
