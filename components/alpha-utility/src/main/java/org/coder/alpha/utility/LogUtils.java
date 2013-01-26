/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.utility;


import org.apache.log4j.Logger;



/**
 * Logger for debugging.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LogUtils {
	
	public static final String DEBUG = "Debug.";
	
	public static final String PERF = "Perf.";

	/**
	 * @return the call stack.
	 */
	private static String fook(){
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		StackTraceElement application = traces[3];
		return formatString(application);		
	}

	/**
	 * @param elm the stack trace
	 * @return the formated trace
	 */
	private static String formatString(StackTraceElement elm){
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
	 * @see alpha.framework.core.logging.LogWriter#error(java.lang.String)
	 */
	public static void error(Logger logger,String message) {
		logger.error(fook()+message);
	}

	/**
	 * @see alpha.framework.core.logging.LogWriter#error(java.lang.String, java.lang.Throwable)
	 */
	public static void error(Logger logger,String message, Throwable t) {
		logger.error(fook()+message, t);
	}


	/**
	 * @see alpha.framework.core.logging.LogWriter#error(java.lang.Throwable)
	 */
	public static void error(Logger logger,Throwable t) {
		logger.error(fook()+t.getMessage(), t);
	}
}
