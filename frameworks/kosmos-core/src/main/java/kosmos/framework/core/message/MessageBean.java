/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message;

import java.io.Serializable;

/**
 * The message bean.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** the message */
	private final int messageId;
	
	/** the arguments of the message */
	private final Object[] arguments;

	/**
	 * @param message
	 * @param arguments
	 */
	public MessageBean(int messageId ,Object... arguments){
		this.messageId = messageId;
		this.arguments = arguments;
	}
	
	/**
	 * @return the message
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}

}