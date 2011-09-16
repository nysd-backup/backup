/**
 * Use is subject to license terms.
 */
package framework.core.exception.system;

/**
 * 複数件存在エラー.
 *
 * @author yoshida-n
 * @version	2011/04/02 created.
 */
public class UnexpectedMultiResultException extends SystemException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message メッセージ
	 * @param cause 原因
	 */
	public UnexpectedMultiResultException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param message メッセージ
	 */
	public UnexpectedMultiResultException(String message) {
		super(message);
	}

}
