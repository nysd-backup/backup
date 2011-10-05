/**
 * Copyright 2011 the original author
 */
package framework.core.message;

/**
 * A defined message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefinedMessage{

	private static final long serialVersionUID = 1L;

	/** the message */
	private String message;
	/** the level */
	private MessageLevel level;
	/** the flag that represent to notify */
	private boolean notify;
	
	/**
	 * @param message the message
	 * @param level the level
	 * @param notify the notify
	 */
	public DefinedMessage(String message ,MessageLevel level, boolean notify){
	
		this.message = message;
		this.level = level;
		this.notify = notify;
	}

	/**
	 * @return the level
	 */
	public MessageLevel getLevel() {
		return level;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the notify
	 */
	public boolean isNotify() {
		return notify;
	}

}
