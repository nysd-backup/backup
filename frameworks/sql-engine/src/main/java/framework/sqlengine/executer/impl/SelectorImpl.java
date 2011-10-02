/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import framework.sqlengine.executer.Selector;

/**
 * The query engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SelectorImpl implements Selector{

	/**
	 * @see framework.sqlengine.executer.Selector#select(java.sql.PreparedStatement)
	 */
	@Override
	public ResultSet select(PreparedStatement stmt) throws SQLException {
		return stmt.executeQuery();
	}

}
