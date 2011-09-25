/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 型変換エンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface TypeConverter {

	/**
	 * @param cast 変換先クラス
	 * @param resultSet リザルトセット
	 * @param columnLabel カラムラベル
	 * @return オブジェクト
	 * @throws SQLException 例外
	 */
	public Object getParameter(Class<?> cast , ResultSet resultSet , String columnLabel)throws SQLException ;
}
