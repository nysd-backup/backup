/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL発行エンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface Selecter {
	
	/**
	 * @param stmt パラメータ設定済みステートメント
	 * @return 検索結果
	 * @throws SQLException 実行失敗
	 */
	public ResultSet select(PreparedStatement stmt) throws SQLException;
}
