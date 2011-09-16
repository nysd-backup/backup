/**
 * Use is subject to license terms.
 */
package framework.sqlengine.facade;

import java.sql.Connection;

/**
 * SQLエンジンのファサード.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface SQLEngineFacade {

	/**
	 * クエリ実行
	 * @param <T> 型
	 * @param param パラメータ
	 * @param con コネクション
	 * @return 件数
	 */
	public int executeCount(QueryParameter<?> param , Connection con);
	
	/**
	 * クエリ実行
	 * @param <T> 型
	 * @param param パラメータ
	 * @param con コネクション
	 * @return 結果
	 */
	public <T> QueryResult<T> executeQuery(QueryParameter<T> param , Connection con);
	
	/**
	 * フェッチして取得
	 * @param <T> 型
	 * @param param パラメータ
	 * @param con コネクション
	 * @return 結果
	 */
	public <T> QueryResult<T> executeFetch(QueryParameter<T> param , Connection con);
	
	/**
	 * トータル件数取得あり
	 * @param <T> 型
	 * @param param パラメータ
	 * @param con コネクション
	 * @return 結果
	 */
	public <T> QueryResult<T> executeTotalQuery(QueryParameter<T> param , Connection con);
	
	/**
	 * 更新
	 * @param parameter パラメータ
	 * @param con コネクション
	 * @return 更新件数
	 */
	public int executeUpdate(UpdateParameter parameter ,Connection con);
}
