/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.coder.alpha.jdbc.domain.TotalList;





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
	 * @param offset the firstResult
	 * @param filter the filter for ResultSet
	 * @return the result
	 * @throws SQLException the exception
	 */
	<T> TotalList<T> getResultList(ResultSet rs, Class<T> resultType,RecordFilter filter,int maxSize)
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
