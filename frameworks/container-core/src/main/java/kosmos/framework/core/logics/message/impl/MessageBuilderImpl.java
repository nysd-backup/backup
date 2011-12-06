/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.message.impl;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.message.MessageBean;


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
	public String load(MessageBean bean){
		return load(bean,"META-INF/message");
	}
	
	/**
	 * @see kosmos.framework.core.logics.message.MessageBuilder#load(kosmos.framework.core.message.MessageBean, java.lang.String)
	 */
	@Override
	public String load(MessageBean bean, String base){
		ResourceBundle bundle = ResourceBundle.getBundle(base, bean.getLocale());
		String message = bundle.getString(String.valueOf(bean.getMessage().getCode()));
		return MessageFormat.format(message, bean.getArguments());
		
	}

}
