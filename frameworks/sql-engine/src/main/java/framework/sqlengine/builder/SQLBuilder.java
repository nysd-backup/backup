/**
 * Use is subject to license terms.
 */
package framework.sqlengine.builder;

import java.util.List;
import java.util.Map;


/**
 * SQLを読み取る.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface SQLBuilder {
	
	/**
	 * @param rowString　SQL文字列/フファイルパス
	 * @param queryId クエリID
	 * @return SQL
	 */
	public String build(String queryId ,String rowString);
	
	/**
	 * @param query SQL
	 * @param parameter if文用パラメータ
	 * @return SQL
	 */
	public String evaluate(String query , Map<String,Object> parameter , String queryId);

	/**
	 * SQL内部の:paramをPreparedStatement用の?に変換する.
	 * 
	 * @param originalSql SQL
	 * @param parameter パラメータ
	 * @param queryId SQLID
	 * @return 変換後SQL
	 */
	public String replaceToPreparedSql(String originalSql,Map<String,Object> parameter,List<Object> bindList, String queryId);
	
	/**
	 * 先頭行取得SQLを設定する.
	 * 
	 * @param sql SQL
	 * @param firstResult 先頭位置
	 * @param getSize 取得件数
	 * @return 先頭位置付きSQL
	 */
	public String setRange(String sql , int firstResult , int getSize , List<Object> bindList);
	
	/**
	 * 先頭行取得SQLを設定する.
	 * 
	 * @param sql SQL
	 * @return 先頭位置付きSQL
	 */
	public String setCount(String sql);
}


