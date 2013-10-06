/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The factory to create the <code>RecordHandler</code>.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface MetadataMapperFactory {

	/**
	 * @param <T> the type
	 * @param type the type
	 * @param rs the result
	 * @return the handler
	 * @throws SQLException 例外
	 */
	MetadataMapper create(Class<?> type , ResultSet rs) throws SQLException;
}
