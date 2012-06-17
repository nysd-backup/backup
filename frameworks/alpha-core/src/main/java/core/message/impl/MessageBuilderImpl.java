/**
 * Copyright 2011 the original author
 */
package core.message.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import core.message.MessageBean;
import core.message.MessageBuilder;
import core.message.MessageLevel;
import core.message.MessageResult;



/**
 * The builder to create the message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBuilderImpl implements MessageBuilder{
	
	/**
	 * @see core.message.MessageBuilder#load(core.message.MessageBean)
	 */
	@Override
	public MessageResult load(MessageBean bean,Locale locale){
		return load(bean,locale,"META-INF/message");
	}
	
	/**
	 * @see core.message.MessageBuilder#load(core.message.MessageBean, java.lang.String)
	 */
	@Override
	public MessageResult load(MessageBean bean, Locale locale,String base){
	
		ResourceBundle bundle = ResourceBundle.getBundle(base, locale==null?Locale.getDefault():locale);
		String message = bundle.getString(String.valueOf(bean.getMessageId()));
		String[] splited = message.split(",");
		MessageResult result = new MessageResult();
		result.setCode(Integer.parseInt(splited[0]));
		result.setMessage(MessageFormat.format(splited[1], bean.getArguments()));
		result.setLevel(MessageLevel.valueOf(splited[2]).ordinal());
		if(splited.length > 2){
			result.setShouldNotify(Boolean.parseBoolean(splited[2]));
		}
		result.setClientBean(bean.getClientBean());
		return result;
	}

}
