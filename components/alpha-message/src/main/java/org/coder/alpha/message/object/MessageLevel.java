/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message.object;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public enum MessageLevel {

	INFO,
	WARN,
	ERROR,
	FATAL;
	
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
