/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import kosmos.framework.sqlengine.executer.Updater;


/**
 * The updating engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UpdaterImpl implements Updater{

	/**
	 * @see kosmos.framework.sqlengine.executer.Updater#update(java.sql.PreparedStatement)
	 */
	@Override
	public int update(PreparedStatement stmt) throws SQLException {
		return stmt.executeUpdate();
	}

	/**
	 * @see kosmos.framework.sqlengine.executer.Updater#batchUpdate(java.sql.PreparedStatement)
	 */
	@Override
	public int[] batchUpdate(PreparedStatement stmt) throws SQLException {
		int[] result = stmt.executeBatch();
		stmt.clearParameters();
		return result;
	}

}
