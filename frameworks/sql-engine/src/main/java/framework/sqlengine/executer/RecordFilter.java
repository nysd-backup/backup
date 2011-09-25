/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer;

/**
 * 検索結果の行ごとに実行されるフィルター.
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
