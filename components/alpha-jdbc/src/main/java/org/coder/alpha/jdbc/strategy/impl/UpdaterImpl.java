/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.coder.alpha.jdbc.strategy.Updater;





/**
 * The updating engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UpdaterImpl implements Updater{

	/**
	 * @see org.coder.alpha.jdbc.strategy.Updater#update(java.sql.PreparedStatement)
	 */
	@Override
	public int update(PreparedStatement stmt) throws SQLException {
		return stmt.executeUpdate();
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.Updater#batchUpdate(java.sql.PreparedStatement)
	 */
	@Override
	public int[] batchUpdate(PreparedStatement stmt) throws SQLException {
		int[] result = stmt.executeBatch();
		stmt.clearParameters();
		return result;
	}

}
