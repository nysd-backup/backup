/**
 * Use is subject to license terms.
 */
package framework.sqlengine.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Statementを作成する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface StatementProvider {
	
	/**
	 * ステートメントを取得する.
	 * 
	 * @param con コネクション
	 * @param sql SQL
	 * @param parameter パラメータ
	 * @param queryId SQLID
	 * @return ステートメント
	 */
	public PreparedStatement createStatement(Connection con ,String sql , List<Object> parameter, String queryId) throws SQLException;

}
