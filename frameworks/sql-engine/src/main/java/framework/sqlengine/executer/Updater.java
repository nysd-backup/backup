/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 更新エンジン.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Updater {
	
	/**
	 * @param stmt　パラメータ設定済みステートメント
	 * @return 更新結果
	 * @throws SQLException 実行失敗
	 */
	public int update(PreparedStatement stmt) throws SQLException;
}
