/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 型変換エンジン.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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
