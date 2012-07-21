/**
 * Copyright 2011 the original author
 */
package sqlengine.builder;

import sqlengine.facade.QueryParameter;

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
	String setExternalString(QueryParameter param , String sql);
}
