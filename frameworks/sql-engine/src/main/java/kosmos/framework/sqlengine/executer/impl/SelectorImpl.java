/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kosmos.framework.sqlengine.executer.Selector;


/**
 * The query engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SelectorImpl implements Selector{

	/**
	 * @see kosmos.framework.sqlengine.executer.Selector#select(java.sql.PreparedStatement)
	 */
	@Override
	public ResultSet select(PreparedStatement stmt) throws SQLException {
		return stmt.executeQuery();
	}

}
