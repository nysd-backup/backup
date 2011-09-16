/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RecordHandlerを生成する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface RecordHandlerFactory {

	/**
	 * @param <T> 型
	 * @param type 結果型
	 * @param rs 結果
	 * @return ハンドラ
	 * @throws SQLException 例外
	 */
	public <T> RecordHandler<T> create(Class<T> type , ResultSet rs) throws SQLException;
}