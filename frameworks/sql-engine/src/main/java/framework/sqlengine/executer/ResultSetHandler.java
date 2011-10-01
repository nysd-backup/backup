/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;

import framework.sqlengine.facade.QueryResult;

/**
 * Handles the <code>ResultSet</code> to get the data.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ResultSetHandler {

	/**
	 * @param <T>　the type
	 * @param rs the rs
	 * @param resultType the resultType
	 * @param maxSize the maxSize
	 * @param totalEnabled if true get the total count hit
	 * @param sqlId the sqlId
	 * @param filter the filter for ResultSet
	 * @return the result
	 * @throws SQLException the exception
	 */
	public <T> QueryResult<T> getResultList(ResultSet rs, Class<T> resultType,int maxSize, boolean totalEnabled, String sqlId,RecordFilter<T> filter)
	throws SQLException ;
}
