/**
 * Copyright 2011 the original author
 */
package framework.logics.log.impl;

import org.apache.log4j.Logger;

import framework.logics.log.LogWriter;

/**
 * ログエンジン.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DebugLogWriterImpl implements LogWriter {

	/** デバッグログ */
	private final Logger logger;

	/**
	 * @return 呼び出しクラス、メソッド、行番号
	 */
	private String fook(){
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		StackTraceElement application = traces[3];
		return formatString(application);		
	}

	/**
	 * @param elm スタックトレース
	 * @return 整形されたスタックトレース文字
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
	 * @param clazz カテゴリ
	 */
	public DebugLogWriterImpl(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}

	@Override
	public void trace(String message) {
		if( isTraceEnabled())logger.trace(fook()+message);
	}

	@Override
	public void debug(String message) {
		if(isDebugEnabled())logger.debug(fook()+message);
	}

	@Override
	public void info(String message) {
		if(isInfoEnabled())logger.info(fook()+message);
	}

	@Override
	public void error(String message) {
		logger.error(fook()+message);
	}

	@Override
	public void error(String message, Throwable t) {
		logger.error(fook()+message, t);
	}


	@Override
	public void error(Throwable t) {
		logger.error(fook()+t.getMessage(), t);
	}

	@Override
	public void warn(String message) {
		logger.warn(fook()+message);
	}

	@Override
	public void warn(String message, Throwable t) {
		logger.warn(fook()+message, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

}
