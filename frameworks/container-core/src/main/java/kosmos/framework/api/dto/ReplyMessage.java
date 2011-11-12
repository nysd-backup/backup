/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.dto;



/**
 * The reply message.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ReplyMessage {

	/** the message */
	private String message = null;
	
	/** the code */
	private int code = -1;
	
	/** the message level */
	private int level = -1;


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

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
}
