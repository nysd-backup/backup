/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.log.impl;

import kosmos.framework.core.logics.log.LogWriter;

import org.apache.log4j.Logger;


/**
 * Logger for debugging.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DebugLogWriterImpl implements LogWriter {

	/** the logger */
	private final Logger logger;

	/**
	 * @return the call stack.
	 */
	private String fook(){
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		StackTraceElement application = traces[3];
		return formatString(application);		
	}

	/**
	 * @param elm the stack trace
	 * @return the formated trace
	 */
	private String formatString(StackTraceElement elm){
		int pos = elm.getClassName().lastIndexOf(".");
		if (pos < 0){
			pos = 0;
		}else {
			pos = pos+1;
		}
		return elm.getClassName().substring(pos)+ "." + elm.getMethodName() +
		(elm.isNativeMethod() ? "(Native Method)" :
		(elm.getFileName() != null && elm.getLineNumber() >= 0 ?
		"(" + elm.getFileName() + ":" +  elm.getLineNumber() + ")" :
		(elm.getFileName() != null ?  "("+elm.getFileName()+")" : "(Unknown Source)")))+":";
	}

	/**
	 * @param clazz the category
	 */
	public DebugLogWriterImpl(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}

	/**
	 * @see kosmos.framework.core.logics.log.NormalLogWriter#trace(java.lang.String)
	 */
	@Override
	public void trace(String message) {
		if( isTraceEnabled())logger.trace(message);
	}

	/**
	 * @see kosmos.framework.core.logics.log.NormalLogWriter#debug(java.lang.String)
	 */
	@Override
	public void debug(String message) {
		if(isDebugEnabled())logger.debug(message);
	}

	/**
	 * @see kosmos.framework.core.logics.log.NormalLogWriter#info(java.lang.String)
	 */
	@Override
	public void info(String message) {
		if(isInfoEnabled())logger.info(message);
	}

	/**
	 * @see kosmos.framework.core.logics.log.LogWriter#error(java.lang.String)
	 */
	@Override
	public void error(String message) {
		logger.error(fook()+message);
	}

	/**
	 * @see kosmos.framework.core.logics.log.LogWriter#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String message, Throwable t) {
		logger.error(fook()+message, t);
	}


	/**
	 * @see kosmos.framework.core.logics.log.LogWriter#error(java.lang.Throwable)
	 */
	@Override
	public void error(Throwable t) {
		logger.error(fook()+t.getMessage(), t);
	}

	/**
	 * @see kosmos.framework.core.logics.log.LogWriter#warn(java.lang.String)
	 */
	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	/**
	 * @see kosmos.framework.core.logics.log.LogWriter#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String message, Throwable t) {
		logger.warn(message, t);
	}

	/**
	 * @see kosmos.framework.core.logics.log.NormalLogWriter#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	 * @see kosmos.framework.core.logics.log.NormalLogWriter#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * @see kosmos.framework.core.logics.log.NormalLogWriter#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

}
