/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message.context;

import org.coder.alpha.message.object.Message;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface MessageHandler {

	public boolean isRollbackOnly(Message message);
	
}
