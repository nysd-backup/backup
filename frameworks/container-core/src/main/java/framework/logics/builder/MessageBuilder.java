/**
 * Copyright 2011 the original author
 */
package framework.logics.builder;

import java.util.Locale;

import framework.core.message.DefinedMessage;
import framework.core.message.MessageBean;

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
	 * @param locale the locale
	 * @return the message
	 */
	public DefinedMessage load(MessageBean bean, Locale locale);
	
	/**
	 * Reads the message definition from the file.
	 * 
	 * @param bean the message bean
	 * @param locale the locale
	 * @param baseFileName the name of message file
	 * @return the message
	 */
	public DefinedMessage load(MessageBean bean, Locale locale, String baseFileName);

	/**
	 * Builds the message.
	 * 
	 * @param message the message
	 * @param arguments the arguments
	 * @return the message
	 */
	public String build(String message, Object... arguments);
}
