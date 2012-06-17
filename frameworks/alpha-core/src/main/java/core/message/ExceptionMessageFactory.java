/**
 * Copyright 2011 the original author
 */
package core.message;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ExceptionMessageFactory {

	/**
	 * @param t
	 * @return
	 */
	MessageBean getBizMessageFrom(Throwable t);
	
	/**
	 * @param t
	 * @return
	 */
	MessageBean getSysMessageFrom(Throwable t);
	
}
