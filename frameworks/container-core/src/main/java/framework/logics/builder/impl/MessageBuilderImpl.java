/**
 * Copyright 2011 the original author
 */
package framework.logics.builder.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import framework.core.message.DefinedMessage;
import framework.core.message.MessageBean;
import framework.core.message.MessageLevel;
import framework.logics.builder.MessageBuilder;

/**
 * The builder to create the message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBuilderImpl implements MessageBuilder{
	
	/**
	 * @see framework.logics.builder.MessageBuilder#load(framework.core.message.MessageBean, java.util.Locale)
	 */
	@Override
	public DefinedMessage load(MessageBean bean, Locale locale){
		return load(bean,locale,"META-INF/message");
	}
	
	/**
	 * @see framework.logics.builder.MessageBuilder#load(framework.core.message.MessageBean, java.util.Locale, java.lang.String)
	 */
	@Override
	public DefinedMessage load(MessageBean bean, Locale locale, String base){
		Locale loc = locale;
		if( locale == null){
			loc = Locale.getDefault();
		}
		ResourceBundle bundle = ResourceBundle.getBundle(base, loc);
		String element = bundle.getString(String.valueOf(bean.getCode()));
		String[] elm = element.split(",");
		MessageLevel level = MessageLevel.find(elm[1]);
		return new DefinedMessage(bean,elm[0],level, "Y".equals(elm[2]));
		
	}

	/**
	 * @see framework.logics.builder.MessageBuilder#build(framework.core.message.DefinedMessage)
	 */
	@Override
	public String build(DefinedMessage defined) {
		return MessageFormat.format(defined.getMessage(),defined.getMessageBean().getDetail());
	}

}
