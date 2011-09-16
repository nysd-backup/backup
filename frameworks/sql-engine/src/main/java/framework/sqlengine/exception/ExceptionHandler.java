/**
 * Use is subject to license terms.
 */
package framework.sqlengine.exception;

/**
 * 例外ハンドラ.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ExceptionHandler {

	/**
	 * @param t 例外
	 * @return rethrow
	 */
	public RuntimeException rethrow(Exception t);
}
