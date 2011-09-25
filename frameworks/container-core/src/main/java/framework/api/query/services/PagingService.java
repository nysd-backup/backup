/**
 * Use is subject to license terms.
 */
package framework.api.query.services;

/**
 * ページング受付サービス.
 *
 * @author yoshida-n
 * @version	2011/06/11 created.
 */
public interface PagingService {

	/**
	 * 初回検索。
	 * @param request　リクエスト
	 * @return 結果
	 */
	public PagingResult prepare(PagingRequest request);
	
	/**
	 * 2回目以降の検索。
	 * @param request リクエスト
	 * @return 結果
	 */
	public PagingResult getPageData(PagingRequest request);
}
