/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.trace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Logger for performance.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultTraceLogger implements TraceLogger{
	
	private static final Log LOG = LogFactory.getLog("PERF." +TraceLogger.class);

	/**
	 * @see org.coder.alpha.framework.trace.TraceLogger#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		LOG.info(message);
	}

	/**
	 * @see org.coder.alpha.framework.trace.TraceLogger#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return LOG.isInfoEnabled();
	}

}
