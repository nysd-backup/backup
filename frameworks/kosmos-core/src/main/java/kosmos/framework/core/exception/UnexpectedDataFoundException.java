/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;

import kosmos.framework.core.message.MessageId;


/**
 * UnexpectedDataFoundException.
 *
 * @author yoshida-n
 * @version	created.
 */
public class UnexpectedDataFoundException extends SystemException{

	/**
	 * 
	 */
	public UnexpectedDataFoundException(){
		super(MessageId.UNEXPECTED_DATAFOUND_ERROR);
	}

	private static final long serialVersionUID = 1L;

}
