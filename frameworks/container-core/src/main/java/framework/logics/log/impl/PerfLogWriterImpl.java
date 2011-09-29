/**
 * Copyright 2011 the original author
 */
package framework.logics.log.impl;

import org.apache.log4j.Logger;

import framework.logics.log.NormalLogWriter;

/**
 * Logger to output the performance log.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PerfLogWriterImpl implements NormalLogWriter {

	/** the logger */
	private final Logger logger;

	/**
	 * @param clazz the category
	 */
	public PerfLogWriterImpl(Class<?> clazz) {
		logger = Logger.getLogger("PERF:" + clazz.getName());
	}

	/**
	 * @see framework.logics.log.LogWriter#trace(java.lang.String)
	 */
	@Override
	public void trace(String message) {
		if( isTraceEnabled())logger.trace(message);
	}

	/**
	 * @see framework.logics.log.LogWriter#debug(java.lang.String)
	 */
	@Override
	public void debug(String message) {
		if(isDebugEnabled())logger.debug(message);
	}

	/**
	 * @see framework.logics.log.LogWriter#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		if(isInfoEnabled())logger.info(message);
	}

	/**
	 * @see framework.logics.log.NormalLogWriter#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	 * @see framework.logics.log.NormalLogWriter#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * @see framework.logics.log.NormalLogWriter#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

}
