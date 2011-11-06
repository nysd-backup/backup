/**
 * Copyright 2011 the original author
 */
package framework.core.message;


/**
 * The message bean.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class ErrorMessage extends WarningMessage{
	
	/** the level */
	public static final int LEVEL = WarningMessage.LEVEL + 1;

	/**
	 * @param code　the code
	 */
	public ErrorMessage(int code){
		super(code);
	}

	/**
	 * @see framework.core.message.AbstractMessage#getLevel()
	 */
	@Override
	public int getLevel() {
		return LEVEL;
	}
	
	

}
