/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message;

import org.coder.alpha.message.object.Message;
import org.coder.alpha.message.object.MessageLevel;

/**
 * ErrorRollbackOnly.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ErrorMessageHandler implements MessageHandler{

	/**
	 * @see org.coder.alpha.message.MessageHandler#isRollbackOnly(org.coder.alpha.message.object.Message)
	 */
	public boolean shouldRollback(Message message){
		return MessageLevel.ERROR.ordinal() <= message.getMessageLevel();
	}
	
}
