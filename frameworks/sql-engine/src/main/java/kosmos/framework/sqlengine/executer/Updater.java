/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The updating engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Updater {
	
	/**
	 * @param stmt　the statement that is binded the parameter.
	 * @return the updated count
	 * @throws SQLException the exception
	 */
	public int update(PreparedStatement stmt) throws SQLException;
}
