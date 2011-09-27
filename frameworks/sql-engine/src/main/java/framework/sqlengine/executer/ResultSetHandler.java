/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;

import framework.sqlengine.facade.QueryResult;

/**
 * ResultSetからデータを取得する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ResultSetHandler {

	/**
	 * @param <T>　型
	 * @param rs 結果
	 * @param resultType 結果型
	 * @param maxSize 最大件数
	 * @param totalEnabled 総件数取得有無
	 * @param sqlId SQLID
	 * @param filter フィルター
	 * @return 結果
	 * @throws SQLException SQL例外
	 */
	public <T> QueryResult<T> getResultList(ResultSet rs, Class<T> resultType,int maxSize, boolean totalEnabled, String sqlId,RecordFilter<T> filter)
	throws SQLException ;
}
