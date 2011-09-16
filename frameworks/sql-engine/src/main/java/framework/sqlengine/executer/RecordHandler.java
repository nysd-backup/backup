/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;

/**
 * ResultSetの1行を取得する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface RecordHandler<T> {
	
	/**
	 * @param rs 結果
	 * @return 結果
	 */
	public T getRecord(ResultSet rs);
}
