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
public class InfoMessage extends AbstractMessage{
	
	/** the level */
	public static final int LEVEL = 1;

	/**
	 * @param code　the code
	 */
	public InfoMessage(int code){
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
