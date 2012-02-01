/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kosmos.framework.sqlengine.facade.QueryResult;


/**
 * Handles the <code>ResultSet</code> to get the data.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ResultSetHandler {
	
	/**
	 * Skips the cursor to specified position.
	 * 
	 * @param rs the ResultSet
	 * @param firstResult the firstResult
	 * @throws SQLException the exception
	 */
	void skip(ResultSet rs , int firstResult) throws SQLException;

	/**
	 * @param <T>　the type
	 * @param rs the rs
	 * @param resultType the resultType
	 * @param maxSize the maxSize
	 * @param firstResult the firstResult
	 * @param filter the filter for ResultSet
	 * @return the result
	 * @throws SQLException the exception
	 */
	QueryResult getResultList(ResultSet rs, Class<?> resultType,RecordFilter filter,int maxSize,int firstResult)
	throws SQLException ;
	
	/**
	 * @param <T>　the type
	 * @param rs the rs
	 * @param resultType the resultType
	 * @param filter the filter for ResultSet
	 * @return the result
	 * @throws SQLException the exception
	 */
	<T> List<T> getResultList(ResultSet rs, Class<?> resultType,RecordFilter filter)
	throws SQLException ;
}
