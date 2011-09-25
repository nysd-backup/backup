/**
 * Use is subject to license terms.
 */
package framework.logics.log;

/**
 * ログエンジン.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public interface LogWriter extends NormalLogWriter{

	/**
	 * @param message エラーログメッセージ
	 */
	public void error(String message);

	/**
	 * @param message エラーメッセージ
	 * @param t エラー
	 */
	public void error(String message, Throwable t);

	/**
	 * @param t 例外
	 */
	public void error(Throwable t);

	/**
	 * @param message 警告ログメッセージ
	 */
	public void warn(String message);

	/**
	 * @param message 警告メッセージ
	 * @param t 例外
	 */
	public void warn(String message, Throwable t);


}