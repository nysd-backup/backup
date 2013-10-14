package org.coder.gear.message;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Message.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class Message implements Serializable{

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** messageId. */
	private String messageId = null;
	
	/** message. */
	private String message = null;
	
	/** messageLevel. */
	private int messageLevel = MessageLevel.INFO.ordinal();
	
	/** notifiable. */
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
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(messageId).append(",").append(message).append(",").append(messageLevel);
		return builder.toString();
	}
	

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return toString().equals(obj.toString());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
	
}
