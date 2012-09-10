/**
 * Copyright 2011 the original author
 */
package alpha.framework.core.message.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import alpha.framework.core.message.Message;
import alpha.framework.core.message.MessageArgument;
import alpha.framework.core.message.MessageBuilder;
import alpha.framework.core.message.MessageLevel;




/**
 * The builder to create the message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBuilderImpl implements MessageBuilder{
	
	/**
	 * @see alpha.framework.core.message.MessageBuilder#load(alpha.framework.core.message.MessageArgument)
	 */
	@Override
	public Message load(MessageArgument bean,Locale locale){
		return load(bean,locale,"META-INF/message");
	}
	
	/**
	 * @see alpha.framework.core.message.MessageBuilder#load(alpha.framework.core.message.MessageArgument, java.lang.String)
	 */
	@Override
	public Message load(MessageArgument bean, Locale locale,String base){
	
		ResourceBundle bundle = ResourceBundle.getBundle(base, locale==null?Locale.getDefault():locale);
		String message = bundle.getString(String.valueOf(bean.getMessageId()));
		String[] splited = message.split(",");
		Message result = new Message();
		result.setCode(splited[0]);
		result.setMessage(MessageFormat.format(splited[1], bean.getArguments()));
		result.setLevel(MessageLevel.valueOf(splited[2]).ordinal());
		if(splited.length > 3){
			result.setShouldNotify(Boolean.parseBoolean(splited[3]));
		}
		result.setTargetClients(bean.getTargetClients());
		return result;
	}

}
