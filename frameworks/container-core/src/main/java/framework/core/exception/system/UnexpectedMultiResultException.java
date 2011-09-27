/**
 * Copyright 2011 the original author
 */
package framework.core.exception.system;

/**
 * 複数件存在エラー.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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
