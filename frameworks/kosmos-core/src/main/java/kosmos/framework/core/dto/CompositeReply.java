/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.dto;

import java.io.Serializable;

import kosmos.framework.core.message.MessageResult;

/**
 * CompositeReply.
 *
 * @author yoshida-n
 * @version	created.
 */
public class CompositeReply implements MessageReplyable{
	
	private MessageResult[] messageList = null;
	
	private Serializable data = null;

	/**
	 * @see kosmos.framework.core.dto.MessageReplyable#setMessageList(kosmos.framework.core.message.MessageResult[])
	 */
	@Override
	public void setMessageList(MessageResult[] messageList) {
		this.messageList = messageList;
	}

	/**
	 * @see kosmos.framework.core.dto.MessageReplyable#getMessageList()
	 */
	@Override
	public MessageResult[] getMessageList() {
		return this.messageList;
	}

	/**
	 * @return the data
	 */
	public Serializable getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Serializable data) {
		this.data = data;
	}

}
