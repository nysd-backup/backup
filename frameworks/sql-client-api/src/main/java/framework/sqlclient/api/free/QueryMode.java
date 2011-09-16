/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

/**
 * クエリモード.
 *
 * @author yoshida-n
 * @version　created.
 */
public enum QueryMode {
	
	/** ResultSetをフェッチして取得 */
	Fetch,
	
	/** 実際のヒット件数取得 */
	Total
}
