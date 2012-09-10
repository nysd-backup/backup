/**
 * Copyright 2011 the original author
 */
package alpha.framework.core.message.impl;

import org.apache.log4j.Logger;

import alpha.framework.core.exception.PoorImplementationException;
import alpha.framework.core.message.FaultLogger;
import alpha.framework.core.message.MessageLevel;



/**
 * Notifies the errors.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FaultLoggerImpl implements FaultLogger {
	
	private static final Logger logger = Logger.getLogger("NOTIFY");

	/**
	 * @see alpha.framework.core.message.FaultLogger#notify(int, java.lang.String, int)
	 */
	@Override
	public void notify(String errorCode, String message, int level) {
		String m = String.format("[%d][%s]",errorCode,message);
		if(MessageLevel.F.ordinal() == level){
			logger.fatal(m);
		}else if(MessageLevel.E.ordinal() == level){
			logger.error(m);
		}else if(MessageLevel.W.ordinal() == level){
			logger.warn(m);
		}else if(MessageLevel.I.ordinal() == level){
			logger.info(m);
		}else {
			throw new PoorImplementationException("invalid error level. : level = " + level);
		}
	}

}
