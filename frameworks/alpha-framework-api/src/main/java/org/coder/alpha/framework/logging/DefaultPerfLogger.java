/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Logger for performance.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultPerfLogger implements PerfLogger{
	
	private static final Log LOG = LogFactory.getLog("PERF." +PerfLogger.class);

	/**
	 * @see org.coder.alpha.framework.logging.PerfLogger#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		LOG.info(message);
	}

	/**
	 * @see org.coder.alpha.framework.logging.PerfLogger#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return LOG.isInfoEnabled();
	}

}
