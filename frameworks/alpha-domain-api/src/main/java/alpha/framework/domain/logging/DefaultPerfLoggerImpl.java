/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Logger for performance.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultPerfLoggerImpl implements PerfLogger{
	
	private static final Log LOG = LogFactory.getLog("PERF." +PerfLogger.class);

	/**
	 * @see alpha.framework.domain.logging.PerfLogger#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		LOG.info(message);
	}

	/**
	 * @see alpha.framework.domain.logging.PerfLogger#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return LOG.isInfoEnabled();
	}

}
