/**
 * Copyright 2011 the original author
 */
package kosmos.framework.client.service;

import kosmos.framework.core.message.MessageResult;

/**
 * Message replying interface.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface MessageReplyable {

	public MessageResult[] getMessageList();
	
	public void setMessageList(MessageResult[] messageList);
}
