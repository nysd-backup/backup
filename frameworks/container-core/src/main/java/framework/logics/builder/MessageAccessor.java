/**
 * Copyright 2011 the original author
 */
package framework.logics.builder;

import framework.core.message.MessageBean;


/**
 * An engine of message.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface MessageAccessor<T extends MessageBean> {

	/**
	 * Creates the message.
	 * 
	 * @param code the code
	 * @param args the binding arguments
	 * @return the message
	 */
	public T createMessage(int code , Object... args);
	

	/**
	 * Adds the message to the context.
	 * 
	 * @param message the message
	 */
	public T addMessage(MessageBean message);
	
}
