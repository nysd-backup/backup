/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder;

import kosmos.framework.sqlengine.facade.SQLParameter;

/**
 * Adds the comment to the specified SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface CommentAppender {

	/**
	 * @param param the parameters
	 * @param sql the SQL
	 */
	public String setExternalString(SQLParameter param , String sql);
}
