/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.dto;

import java.io.Serializable;

/**
 * A request data from WEB container
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ReplyDto implements Serializable{

	private static final long serialVersionUID = -5671768150819629678L;
	
	/** the parameter */
	private ReplyMessage[] messages;

	/** the class of target service */
	private Object retValue;

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(ReplyMessage[] messages) {
		this.messages = messages;
	}

	/**
	 * @return the messages
	 */
	public ReplyMessage[] getMessages() {
		return messages;
	}

	/**
	 * @param retValue the retValue to set
	 */
	public void setRetValue(Object retValue) {
		this.retValue = retValue;
	}

	/**
	 * @return the retValue
	 */
	public Object getRetValue() {
		return retValue;
	}


}
