/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.message;

/**
 * MessageLevel.
 *
 * @author yoshida-n
 * @version	1.0
 */
public enum MessageLevel {

	INFO,
	WARN,
	ERROR,
	FATAL;
	
	/**
	 * @param level the level
	 * @return the MessageLevel
	 */
	public static MessageLevel valueOf(int level){
		if(INFO.ordinal() == level){
			return INFO;
		}else if(WARN.ordinal() == level){
			return WARN;
		}else if(ERROR.ordinal() == level){
			return ERROR;
		}else {
			return FATAL;
		}
	}
}
