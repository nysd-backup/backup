/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.message.impl;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import kosmos.framework.core.context.AbstractContainerContext;
import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.core.message.Messages;


/**
 * The builder to create the message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBuilderImpl implements MessageBuilder{
	
	/**
	 * @see kosmos.framework.core.logics.message.MessageBuilder#load(kosmos.framework.core.message.MessageBean)
	 */
	@Override
	public MessageResult load(MessageBean bean){
		return load(bean,"META-INF/message");
	}
	
	/**
	 * @see kosmos.framework.core.logics.message.MessageBuilder#load(kosmos.framework.core.message.MessageBean, java.lang.String)
	 */
	@Override
	public MessageResult load(MessageBean bean, String base){
		AbstractContainerContext context = AbstractContainerContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle(base, context.getLocale());
		String message = bundle.getString(String.valueOf(bean.getMessageId()));
		String[] splited = message.split(",");
		MessageResult result = new MessageResult();
		result.setCode(Integer.parseInt(splited[0]));
		result.setMessage(MessageFormat.format(splited[1], bean.getArguments()));
		result.setLevel(Messages.Level.valueOf(splited[2]).ordinal());
		return result;
	}

}
