/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message;


/**
 * The message bean.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class FatalMessage extends ErrorMessage{
	
	/** the level */
	public static final int LEVEL = ErrorMessage.LEVEL + 1;

	/**
	 * @param code　the code
	 */
	public FatalMessage(int code){
		super(code);
	}

	/**
	 * @see kosmos.framework.core.message.AbstractMessage#getLevel()
	 */
	@Override
	public int getLevel() {
		return LEVEL;
	}
	
	

}
