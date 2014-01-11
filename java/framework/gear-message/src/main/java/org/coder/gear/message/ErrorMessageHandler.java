/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.message;


/**
 * ErrorRollbackOnly.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class ErrorMessageHandler implements MessageHandler{

	/**
	 * @see org.coder.gear.message.MessageHandler#isRollbackOnly(org.coder.gear.trace.alpha.message.object.Message)
	 */
	public boolean shouldRollback(Message message){
		return MessageLevel.ERROR.ordinal() <= message.getMessageLevel();
	}
	
}
