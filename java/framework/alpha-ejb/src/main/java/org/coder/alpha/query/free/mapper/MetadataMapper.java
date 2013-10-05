/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets the one record from ResultSet.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface MetadataMapper {
	
	/**
	 * @param rs the rs
	 * @return the result 
	 */
	<T> T getRecord(ResultSet rs) throws SQLException;
}
