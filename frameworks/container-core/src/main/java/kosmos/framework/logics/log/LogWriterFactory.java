/**
 * Copyright 2011 the original author
 */
package kosmos.framework.logics.log;

import kosmos.framework.logics.log.impl.DebugLogWriterImpl;
import kosmos.framework.logics.log.impl.PerfLogWriterImpl;

/**
 * The factory to create the logger.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public final class LogWriterFactory {

	/**
	 * Constructor
	 */
	private LogWriterFactory(){
	}
	
	/**
	 * Creates the debugging logger.
	 * 
	 * @param clazz the category
	 * @return the writer
	 */
	public static LogWriter getLog(Class<?> clazz) {
		return new DebugLogWriterImpl(clazz);
	}
	
	/**
	 * Creates the performance logger.
	 * 
	 * @param clazz the category
	 * @return the writer
	 */
	public static NormalLogWriter getPerfLog(Class<?> clazz) {
		return new PerfLogWriterImpl(clazz);
	}
}
