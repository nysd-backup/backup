/**
 * Copyright 2011 the original author
 */
package kosmos.framework.test.service;

import kosmos.framework.core.message.MessageReplyable;
import kosmos.framework.core.message.MessageResult;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Reply implements MessageReplyable{
	
	private MessageResult[] messageList = null;

	/**
	 * @see kosmos.framework.core.message.MessageReplyable#setMessageList(kosmos.framework.core.message.MessageResult[])
	 */
	@Override
	public void setMessageList(MessageResult[] messageList) {
		this.messageList = messageList;
	}

	/**
	 * @see kosmos.framework.core.message.MessageReplyable#getMessageList()
	 */
	@Override
	public MessageResult[] getMessageList() {
		return this.messageList;
	}

}
