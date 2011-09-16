/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface RecordFilter<T> {


	/**
	 * @param data データ
	 */
	public T edit(T data);
}
