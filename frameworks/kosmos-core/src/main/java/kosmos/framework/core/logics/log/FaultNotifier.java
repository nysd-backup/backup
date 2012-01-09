/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.log;


/**
 * Notify fault to fault manager.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface FaultNotifier {

	/**
	 * @param errorCode the errocCode
	 * @param messege the message
	 * @param level the level
	 */
	public void notify(int errorCode, String messege , int level);
}
