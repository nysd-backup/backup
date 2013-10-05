/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * MessageBuilder.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class MessageBuilder {

	/**
	 * messageId .
	 */
	private final String messageId;
	
	/**
	 * the arguments.
	 */
	private Object[] args;
	
	/**
	 * the base name.
	 */
	private String baseName = "META-INF/application_message";
	
	/**
	 * the locale.
	 */
	private Locale locale = Locale.getDefault();
	
	/**
	 * @param messageId to set
	 * @return self
	 */
	public static MessageBuilder messageFor(String messageId){
		return new MessageBuilder(messageId);
	}
	
	/**
	 * @param messageId to set
	 */
	private MessageBuilder(String messageId){
		this.messageId = messageId;
	}

	/**
	 * @param baseName to set
	 * @return self
	 */
	public MessageBuilder withBaseName(String baseName){
		this.baseName = baseName;
		return this;
	}
	
	/**
	 * @param locale to set
	 * @return self
	 */
	public MessageBuilder withLocale(Locale locale){
		this.locale = locale;
		return this;
	}
	
	/**
	 * @param args to set
	 * @return self
	 */
	public MessageBuilder withArgs(Object... args){
		this.args = args;
		return this;
	}
	
	/**
	 * @return the message
	 */
	public Message build(){		
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        String message = bundle.getString(messageId);
        String[] splited = message.split(",");
        Message result = new Message();
        result.setMessageId(splited[0]);
        result.setMessage(MessageFormat.format(splited[1],args));
        result.setMessageLevel(MessageLevel.valueOf(splited[2]).ordinal());
        if (splited.length > 3) {
            result.setNotifiable("Y".equals(splited[3]));
        }
        return result;
	}
}
