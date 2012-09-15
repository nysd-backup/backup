/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import alpha.jdbc.domain.ResultSetWrapper;
import alpha.jdbc.strategy.Selector;




/**
 * The query engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SelectorImpl implements Selector{

	/**
	 * @see alpha.jdbc.strategy.Selector#select(java.sql.PreparedStatement, int)
	 */
	@Override
	public ResultSetWrapper select(PreparedStatement stmt) throws SQLException {
		return new ResultSetWrapper(stmt.executeQuery());
	}

}
