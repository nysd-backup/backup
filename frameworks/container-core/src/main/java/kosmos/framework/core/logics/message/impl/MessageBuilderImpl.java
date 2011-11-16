/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.message.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.message.AbstractMessage;


/**
 * The builder to create the message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBuilderImpl implements MessageBuilder{
	
	/**
	 * @see kosmos.framework.core.logics.message.MessageBuilder#load(kosmos.framework.core.message.AbstractMessage, java.util.Locale, java.lang.Object[])
	 */
	@Override
	public String load(AbstractMessage bean, Locale locale, Object... args){
		return load(bean,locale,"META-INF/message", args);
	}
	
	/**
	 * @see kosmos.framework.core.logics.message.MessageBuilder#load(kosmos.framework.core.message.AbstractMessage, java.util.Locale, java.lang.String, java.lang.Object[])
	 */
	@Override
	public String load(AbstractMessage bean, Locale locale, String base, Object... args){
		Locale loc = locale;
		if( locale == null){
			loc = Locale.getDefault();
		}
		ResourceBundle bundle = ResourceBundle.getBundle(base, loc);
		String message = bundle.getString(String.valueOf(bean.getCode()));
		return MessageFormat.format(message, args);
		
	}

}
