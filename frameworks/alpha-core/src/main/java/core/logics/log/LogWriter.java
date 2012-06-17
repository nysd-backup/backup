/**
 * Copyright 2011 the original author
 */
package core.logics.log;

/**
 * Logger for debugging.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface LogWriter extends NormalLogWriter{

	/**
	 * @param message the message
	 */
	public void error(String message);

	/**
	 * @param message the message
	 * @param t the exception
	 */
	public void error(String message, Throwable t);

	/**
	 * @param t the exception
	 */
	public void error(Throwable t);

	/**
	 * @param message the message
	 */
	public void warn(String message);

	/**
	 * @param message the message
	 * @param t the exception
	 */
	public void warn(String message, Throwable t);


}
