/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.message;

import java.util.Locale;

import kosmos.framework.core.message.AbstractMessage;


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
	 * @param args the args
	 * @return the message
	 */
	public String load(AbstractMessage bean, Locale locale, Object... args);
	
	/**
	 * Reads the message definition from the file.
	 * 
	 * @param bean the message bean
	 * @param locale the locale
	 * @param baseFileName the name of message file
	 * @param args the args
	 * @return the message
	 */
	public String load(AbstractMessage bean, Locale locale, String baseFileName , Object... args);

}
