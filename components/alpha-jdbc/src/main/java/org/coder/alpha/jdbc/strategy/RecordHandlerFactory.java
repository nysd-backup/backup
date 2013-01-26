/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy;

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
	RecordHandler create(Class<?> type , ResultSet rs) throws SQLException;
}
