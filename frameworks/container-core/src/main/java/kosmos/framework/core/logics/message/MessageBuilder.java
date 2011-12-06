/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.message;

import kosmos.framework.core.message.MessageBean;


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
	public String load(MessageBean bean);
	
	/**
	 * Reads the message definition from the file.
	 * 
	 * @param bean the message bean
	 * @param baseFileName the name of message file
	 * @return the message
	 */
	public String load(MessageBean bean, String baseFileName);

}
