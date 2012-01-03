/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message;

/**
 * A result of the messageId.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageResult {
	
	/** the cod */
	private int code;
	
	/** the message level */
	private int level;
	
	/** the message */
	private String message;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
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

}
