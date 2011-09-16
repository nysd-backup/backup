/**
 * Use is subject to license terms.
 */
package framework.web.core.api.query.paging;

/**
 * ページングコンテキストファクトリ.
 *
 * @author yoshida-n
 * @version	2011/06/14 created.
 */
public interface PagingContexFactory {

	/**
	 * @return コンテキスト
	 */
	public PagingContext create();
}
