/**
 * Copyright 2011 the original author
 */
package alpha.framework.core.message;


/**
 * Notify fault to fault manager.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface FaultLogger {

	/**
	 * @param errorCode the errocCode
	 * @param messege the message
	 * @param level the level
	 */
	public void notify(String errorCode, String messege , int level);
}
