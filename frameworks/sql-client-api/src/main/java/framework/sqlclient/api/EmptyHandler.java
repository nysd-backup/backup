/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api;


/**
 * 検索結果0件時の処理.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface EmptyHandler {

	/**
	 * @param condition 検索条件
	 */
	public void handleEmptyResult(Object condition);
}
