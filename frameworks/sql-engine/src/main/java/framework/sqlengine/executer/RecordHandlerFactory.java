/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The factory to create the <code>RecordHandler</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RecordHandlerFactory {

	/**
	 * @param <T> the type
	 * @param type the type
	 * @param rs the result
	 * @return the handler
	 * @throws SQLException 例外
	 */
	public <T> RecordHandler<T> create(Class<T> type , ResultSet rs) throws SQLException;
}
