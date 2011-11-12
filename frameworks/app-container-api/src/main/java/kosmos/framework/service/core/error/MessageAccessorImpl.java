/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.error;

import java.util.Locale;

import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.logics.builder.MessageAccessor;
import kosmos.framework.logics.builder.MessageBuilder;
import kosmos.framework.service.core.transaction.ServiceContext;


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
	 * @see kosmos.framework.logics.builder.MessageAccessor#addMessage(kosmos.framework.core.message.AbstractMessage)
	 */
	@Override
	public <T extends ErrorMessage> T addMessage(T message,Object... args) {
		return addMessage(Locale.getDefault(),message,args);
	}
	
	/**
	 * @see kosmos.framework.logics.builder.MessageAccessor#addMessage(kosmos.framework.core.message.AbstractMessage)
	 */
	@Override
	public <T extends ErrorMessage> T addMessage(Locale locale,T message,Object... args) {
		ServiceContext context = ServiceContext.getCurrentInstance();	
		String defined = builder.load(message, locale);	
		context.addMessage(message,builder.build(defined,args));
		return message;
	}

}
