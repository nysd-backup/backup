/**
 * Copyright 2011 the original author
 */
package alpha.framework.core.base;

import java.util.List;

import alpha.framework.core.message.Message;


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
	public List<Message> getMessages();
	
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<Message> messages);
}
