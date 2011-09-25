/**
 * Use is subject to license terms.
 */
package framework.core.exception.system;

/**
 * 0件エラー.
 *
 * @author yoshida-n
 * @version	2011/06/24 created.
 */
public class UnexpectedNoDataFoundException extends SystemException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message メッセージ
	 * @param cause 原因
	 */
	public UnexpectedNoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param message メッセージ
	 */
	public UnexpectedNoDataFoundException(String message) {
		super(message);
	}
}
