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
public interface PerfLogger {

	/**
	 * @param message the info message
	 */
	void info(String message);

	/**
	 * @return true:info enabled
	 */
	boolean isInfoEnabled();
	
}
