/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;

/**
 * ResultSetの1行を取得する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RecordHandler<T> {
	
	/**
	 * @param rs 結果
	 * @return 結果
	 */
	public T getRecord(ResultSet rs);
}
