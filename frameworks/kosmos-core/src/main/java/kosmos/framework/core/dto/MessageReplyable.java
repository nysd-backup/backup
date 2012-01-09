/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.dto;

import kosmos.framework.core.message.MessageResult;

/**
 * Replyable message.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface MessageReplyable {

	/**
	 * @param messageList the messageList to set
	 */
	public void setMessageList(MessageResult[] messageList);
	
	/**
	 * @return the message list
	 */
	public MessageResult[] getMessageList();
}
