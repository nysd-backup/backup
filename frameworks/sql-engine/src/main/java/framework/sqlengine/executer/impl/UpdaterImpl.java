/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import framework.sqlengine.executer.Updater;

/**
 * SQL発行処理、リトライ等が必要な場合にはここで対応する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UpdaterImpl implements Updater{

	/**
	 * @see framework.sqlengine.executer.Updater#update(java.sql.PreparedStatement)
	 */
	@Override
	public int update(PreparedStatement stmt) throws SQLException {
		return stmt.executeUpdate();
	}

}
