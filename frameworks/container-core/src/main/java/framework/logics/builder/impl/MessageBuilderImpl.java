/**
 * Copyright 2011 the original author
 */
package framework.logics.builder.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import framework.core.message.AbstractMessage;
import framework.logics.builder.MessageBuilder;

/**
 * The builder to create the message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBuilderImpl implements MessageBuilder{
	
	/**
	 * @see framework.logics.builder.MessageBuilder#load(framework.core.message.AbstractMessage, java.util.Locale)
	 */
	@Override
	public String load(AbstractMessage bean, Locale locale){
		return load(bean,locale,"META-INF/message");
	}
	
	/**
	 * @see framework.logics.builder.MessageBuilder#load(framework.core.message.AbstractMessage, java.util.Locale, java.lang.String)
	 */
	@Override
	public String load(AbstractMessage bean, Locale locale, String base){
		Locale loc = locale;
		if( locale == null){
			loc = Locale.getDefault();
		}
		ResourceBundle bundle = ResourceBundle.getBundle(base, loc);
		return bundle.getString(String.valueOf(bean.getCode()));
		
	}

	/**
	 * @see framework.logics.builder.MessageBuilder#build(framework.core.message.DefinedMessage)
	 */
	@Override
	public String build(String message, Object... arguments) {
		return MessageFormat.format(message,arguments);
	}

}
