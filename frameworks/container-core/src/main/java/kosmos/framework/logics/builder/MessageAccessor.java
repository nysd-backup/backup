/**
 * Copyright 2011 the original author
 */
package kosmos.framework.logics.builder;

import java.util.Locale;

import kosmos.framework.core.message.ErrorMessage;



/**
 * An engine of message.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface MessageAccessor {

	/**
	 * Adds the message to the context.
	 * 
	 * @param message the message
	 * @param locale the locale
	 * @param args the args
	 */
	public <T extends ErrorMessage> T addMessage(Locale locale,T message,Object... args);
	
	/**
	 * Adds the message to the context.
	 * 
	 * @param message the message
	 * @param args the args
	 */
	public <T extends ErrorMessage> T addMessage(T message,Object... args);
	
}
