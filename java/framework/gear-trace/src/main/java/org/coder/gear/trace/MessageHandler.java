/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.trace;


/**
 * MessageHandler.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface MessageHandler {

	/**
	 * @param message the message
	 * @return true:rollback only
	 */
	public boolean shouldRollback(Message message);
	
}
