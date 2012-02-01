/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.log.impl;

import org.apache.log4j.Logger;

import kosmos.framework.core.exception.PoorImplementationException;
import kosmos.framework.core.logics.log.FaultNotifier;
import kosmos.framework.core.message.MessageLevel;

/**
 * Notifies the errors.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultFaultNotifier implements FaultNotifier {
	
	private static final Logger logger = Logger.getLogger("NOTIFY");

	/**
	 * @see kosmos.framework.core.logics.log.FaultNotifier#notify(int, java.lang.String, int)
	 */
	@Override
	public void notify(int errorCode, String message, int level) {
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
