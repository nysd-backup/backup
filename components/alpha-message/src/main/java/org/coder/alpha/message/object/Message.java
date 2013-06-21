package org.coder.alpha.message.object;

import java.io.Serializable;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;

	private String messageId = null;
	
	private String message = null;
	
	private int messageLevel = MessageLevel.INFO.ordinal();
	
	private boolean notifiable = false;
	
	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the messageLevel
	 */
	public int getMessageLevel() {
		return messageLevel;
	}

	/**
	 * @param messageLevel the messageLevel to set
	 */
	public void setMessageLevel(int messageLevel) {
		this.messageLevel = messageLevel;
	}

	/**
	 * @return the notifiable
	 */
	public boolean isNotifiable() {
		return notifiable;
	}

	/**
	 * @param notifiable the notifiable to set
	 */
	public void setNotifiable(boolean notifiable) {
		this.notifiable = notifiable;
	}
	
}
