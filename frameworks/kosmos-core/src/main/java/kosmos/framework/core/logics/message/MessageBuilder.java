/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.message;

import java.util.Locale;

import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageResult;


/**
 * A builder to create the message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface MessageBuilder {

	/**
	 * Reads the message definition from the file.
	 * 
	 * @param bean the message bean
	 * @return the message
	 */
	public MessageResult load(MessageBean bean,Locale locale);
	
	/**
	 * Reads the message definition from the file.
	 * 
	 * @param bean the message bean
	 * @param baseFileName the name of message file
	 * @return the message
	 */
	public MessageResult load(MessageBean bean, Locale locale,String baseFileName);

}
