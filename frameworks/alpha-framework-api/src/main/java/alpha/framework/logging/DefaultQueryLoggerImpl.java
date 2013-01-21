/**
 * Copyright 2011 the original author
 */
package alpha.framework.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Logger for query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultQueryLoggerImpl implements QueryLogger{
	
	private static final Log LOG = LogFactory.getLog("QUERY." +QueryLogger.class);

	/**
	 * @see alpha.framework.logging.QueryLogger#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		LOG.info(message);
	}

	/**
	 * @see alpha.framework.logging.QueryLogger#debug(java.lang.String)
	 */
	@Override
	public void debug(String message) {
		LOG.debug(message);
	}

	/**
	 * @see alpha.framework.logging.QueryLogger#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return LOG.isInfoEnabled();
	}

	/**
	 * @see alpha.framework.logging.QueryLogger#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return LOG.isDebugEnabled();
	}

	/**
	 * @see alpha.framework.logging.QueryLogger#trace(java.lang.String)
	 */
	@Override
	public void trace(String message) {
		LOG.trace(message);
	}

	/**
	 * @see alpha.framework.logging.QueryLogger#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return LOG.isTraceEnabled();
	}

}
