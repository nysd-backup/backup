/**
 * Use is subject to license terms.
 */
package framework.sqlengine.builder;

import framework.sqlengine.facade.SQLParameter;

/**
 * SQLにコメントを設定する.
 *
 * @author yoshida-n
 * @version created.
 */
public interface CommentAppender {

	/**
	 * @param param パラメータ
	 * @param sql SQL
	 */
	public String setExternalString(SQLParameter param , String sql);
}
