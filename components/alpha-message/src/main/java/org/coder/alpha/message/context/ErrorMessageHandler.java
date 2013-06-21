/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message.context;

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
	 * @see org.coder.alpha.message.context.MessageHandler#isRollbackOnly(org.coder.alpha.message.object.Message)
	 */
	public boolean isRollbackOnly(Message message){
		return MessageLevel.ERROR.ordinal() <= message.getMessageLevel();
	}
	
}
