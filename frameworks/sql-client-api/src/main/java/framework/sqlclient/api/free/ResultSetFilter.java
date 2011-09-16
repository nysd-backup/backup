/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

/**
 * ResultSet用のフィルター.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ResultSetFilter<T> {

	/**
	 * @param data データ
	 */
	public T edit(T data);
}
