/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;

/**
 * Handles the Throwable.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ExceptionHandler {

	/**
	 * @param t the Throwable
	 * @return the rethrowable exception
	 */
	public Throwable handle(Throwable t);
}
