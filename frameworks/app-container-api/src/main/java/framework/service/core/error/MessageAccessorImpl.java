/**
 * Copyright 2011 the original author
 */
package framework.service.core.error;

import java.util.Locale;

import framework.core.message.ErrorMessage;
import framework.logics.builder.MessageAccessor;
import framework.logics.builder.MessageBuilder;
import framework.service.core.transaction.ServiceContext;

/**
 * A message handler.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageAccessorImpl implements MessageAccessor{
	
	/** the builder for message */
	private MessageBuilder builder;
	
	/**
	 * @param builder the builder to set
	 */
	public void setMessageBuilder(MessageBuilder builder){
		this.builder = builder;
	}

	/**
	 * @see framework.logics.builder.MessageAccessor#addMessage(framework.core.message.AbstractMessage)
	 */
	@Override
	public <T extends ErrorMessage> T addMessage(T message,Object... args) {
		return addMessage(Locale.getDefault(),message,args);
	}
	
	/**
	 * @see framework.logics.builder.MessageAccessor#addMessage(framework.core.message.AbstractMessage)
	 */
	@Override
	public <T extends ErrorMessage> T addMessage(Locale locale,T message,Object... args) {
		ServiceContext context = ServiceContext.getCurrentInstance();	
		String defined = builder.load(message, locale);	
		context.addError(message,builder.build(defined,args));
		return message;
	}

}
