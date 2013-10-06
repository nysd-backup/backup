/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.message;


/**
 * ErrorRollbackOnly.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class ErrorMessageHandler implements MessageHandler{

	/**
	 * @see org.coder.alpha.message.MessageHandler#isRollbackOnly(org.coder.alpha.message.object.Message)
	 */
	public boolean shouldRollback(Message message){
		return MessageLevel.ERROR.ordinal() <= message.getMessageLevel();
	}
	
}
