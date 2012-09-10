/**
 * Copyright 2011 the original author
 */
package sqlengine.strategy.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import sqlengine.strategy.Updater;



/**
 * The updating engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UpdaterImpl implements Updater{

	/**
	 * @see sqlengine.strategy.Updater#update(java.sql.PreparedStatement)
	 */
	@Override
	public int update(PreparedStatement stmt) throws SQLException {
		return stmt.executeUpdate();
	}

	/**
	 * @see sqlengine.strategy.Updater#batchUpdate(java.sql.PreparedStatement)
	 */
	@Override
	public int[] batchUpdate(PreparedStatement stmt) throws SQLException {
		int[] result = stmt.executeBatch();
		stmt.clearParameters();
		return result;
	}

}
