/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api;


/**
 * 複数件取得時処理.
 *
 * @author yoshida-n
 * @version	2011/02/26 created.
 */
public interface MultiResultHandler {

	/**
	 * @param condition 検索条件
	 */
	public void handleResult(Object condition);
}
