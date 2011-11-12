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
public abstract class AbstractMessage{

	/** the message code  */
	private final int code;
	
	/**
	 * @param code　the code
	 * @param args the arguments
	 */
	public AbstractMessage(int code){
		this.code = code;
	}
	
	/**
	 * get code
	 *
	 * @return code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * @return the level
	 */
	public abstract int getLevel();

}
