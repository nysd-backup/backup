/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.logging;

/**
 * Logger for query.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryLogger {

	/**
	 * @param message the info message
	 */
	void info(String message);
	
	/**
	 * @param message the trace message
	 */
	void trace(String message);
	
	/**
	 * @param message the debug message
	 */
	void debug(String message);
	
	/**
	 * @return true:info enabled
	 */
	boolean isInfoEnabled();
	
	/**
	 * @return true:info enabled
	 */
	boolean isTraceEnabled();
	
	/**
	 * @return true:debug enabled
	 */
	boolean isDebugEnabled();
}
