/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message;


/**
 * MessageHandler.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface MessageHandler {

	/**
	 * @param message the message
	 * @return true:rollback only
	 */
	public boolean shouldRollback(Message message);
	
}
