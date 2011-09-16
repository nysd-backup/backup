package framework.web.core.api.query.paging;

import java.util.List;
import framework.sqlclient.api.free.AbstractNativeQuery;

/**
 * ページングコンテキスト.
 *
 * @author yoshida-n
 * @version	2011/06/12 created.
 */
@SuppressWarnings("rawtypes")
public interface PagingContext {

	/**
	 * @return 現在のページのデータ
	 */
	public abstract List getCurrentPageData();

	/**
	 * @return 総件数
	 */
	public abstract int getTotalCount();

	/**
	 * @return 総ページ数
	 */
	public abstract int getTotalPage();

	/**
	 * @return 現在取得しているデータのページ番号(1～)
	 */
	public abstract int getCurrentPageNo();

	/**
	 * ページング準備.
	 * @param query クエリ
	 * @param pageSize ページサイズ
	 */
	public abstract List prepare(AbstractNativeQuery query, int pageSize);

	/**
	 * ページデータ管理機構に問い合わせてデータをロードする.
	 * @param pageNo ページ番号
	 * @return データ
	 */
	public abstract List getPageData(int pageNo);

}
