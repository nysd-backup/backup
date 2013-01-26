/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.coder.alpha.jdbc.domain.ResultSetWrapper;



/**
 * The query engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Selector {
	
	/**
	 * @param stmt　the statement that is binded the parameter
	 * @param startPosition the position to start
	 * @return the result
	 * @throws SQLException the exception
	 */
	ResultSetWrapper select(PreparedStatement stmt) throws SQLException;
}
