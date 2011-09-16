/**
 * Use is subject to license terms.
 */
package framework.api.query.services;

import java.util.List;

import framework.sqlclient.api.free.NativeResult;

/**
 * ネイティブクエリ用サービス.
 *
 * @author yoshida-n
 * @version	2011/05/12 created.
 */
public interface NativeQueryService {

	/**
	 * 件数取得.
	 * 
	 * @param request リクエスト
	 * @return 検索結果
	 */
	public int count(QueryRequest request);
	
	/**
	 * 複数件取得.
	 * 
	 * @param request リクエスト
	 * @return 検索結果
	 */
	public <T> List<T> getResultList(QueryRequest request);
	
	/**
	 * 総件数取得.
	 * 
	 * @param request リクエスト
	 * @return 検索結果
	 */
	public <T> NativeResult<T> getTotalResult(QueryRequest request);
	
	/**
	 * 1件取得.
	 * 
	 * @param request リクエスト
	 * @return 検索結果
	 */
	public <T> T getSingleResult(QueryRequest request);
	
	/**
	 * 存在チェック.
	 * @param request リクエスト
	 * @return true:存在する
	 */
	public boolean exists(QueryRequest request);
	
	
}
