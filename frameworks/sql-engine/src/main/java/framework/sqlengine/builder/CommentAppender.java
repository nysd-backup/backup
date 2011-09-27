/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder;

import framework.sqlengine.facade.SQLParameter;

/**
 * SQLにコメントを設定する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface CommentAppender {

	/**
	 * @param param パラメータ
	 * @param sql SQL
	 */
	public String setExternalString(SQLParameter param , String sql);
}
