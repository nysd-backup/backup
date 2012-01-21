/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets the one record from ResultSet.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RecordHandler {
	
	/**
	 * @param rs the rs
	 * @return the result 
	 */
	public <T> T getRecord(ResultSet rs) throws SQLException;
}
