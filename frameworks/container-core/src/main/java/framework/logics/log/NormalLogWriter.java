/**
 * Copyright 2011 the original author
 */
package framework.logics.log;

/**
 * The logger.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NormalLogWriter {

	/**
	 * @param message the message
	 */
	public void debug(String message);

	/**
	 * @param message the message
	 */
	public void info(String message);

	/**
	 * @param message the message
	 */
	public void trace(String message);

	/**
	 * @return the debug mode
	 */
	public boolean isDebugEnabled();

	/**
	 * @return the info mode
	 */
	public boolean isInfoEnabled();

	/**
	 * @return the trace mode
	 */
	public boolean isTraceEnabled();


}
