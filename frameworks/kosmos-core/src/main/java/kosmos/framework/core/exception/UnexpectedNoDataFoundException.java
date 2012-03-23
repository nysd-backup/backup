/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;

import kosmos.framework.core.message.MessageId;


/**
 * UnexpectedNoDataFoundException .
 *
 * @author yoshida-n
 * @version	created.
 */
public class UnexpectedNoDataFoundException extends SystemException{

	/**
	 * 
	 */
	public UnexpectedNoDataFoundException(){
		super(MessageId.UNEXPECTED_NODATA_ERROR);
	}

	private static final long serialVersionUID = 1L;

}
