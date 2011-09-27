/**
 * Copyright 2011 the original author
 */
package framework.core.exception.system;

/**
 * 0件エラー.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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
