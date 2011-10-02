/**
 * Copyright 2011 the original author
 */
package framework.api.dto;

import framework.core.message.MessageBean;

/**
 * The reply message.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ReplyMessage {

	/** the message bean */
	private MessageBean messageBean = null;
	
	/** the message level */
	private int level = -1;

	/**
	 * @param messageBean the messageBean to set
	 */
	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}

	/**
	 * @return the messageBean
	 */
	public MessageBean getMessageBean() {
		return messageBean;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
}
