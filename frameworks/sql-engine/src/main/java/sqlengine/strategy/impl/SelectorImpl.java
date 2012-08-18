/**
 * Copyright 2011 the original author
 */
package sqlengine.strategy.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import sqlengine.domain.ResultSetWrapper;
import sqlengine.strategy.Selector;



/**
 * The query engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SelectorImpl implements Selector{

	/**
	 * @see sqlengine.strategy.Selector#select(java.sql.PreparedStatement, int)
	 */
	@Override
	public ResultSetWrapper select(PreparedStatement stmt) throws SQLException {
		return new ResultSetWrapper(stmt.executeQuery());
	}

}
