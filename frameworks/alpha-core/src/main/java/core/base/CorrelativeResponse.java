/**
 * Copyright 2011 the original author
 */
package core.base;

import java.util.List;

import core.message.MessageResult;

/**
 * CorrelativeResponse.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface CorrelativeResponse {
	
	/**
	 * @param failed the failed to set
	 */
	public void setFailed(boolean failed);
	
	/**
	 * @return failed
	 */
	public boolean isFailed();

	/**
	 * @return the business messages
	 */
	public List<MessageResult> getMessages();
	
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<MessageResult> messages);
}
