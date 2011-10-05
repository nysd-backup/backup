/**
 * Copyright 2011 the original author
 */
package framework.service.core.error;

import java.util.Locale;

import framework.api.dto.ReplyMessage;
import framework.core.message.DefinedMessage;
import framework.core.message.MessageBean;
import framework.logics.builder.MessageAccessor;
import framework.logics.builder.MessageBuilder;
import framework.service.core.transaction.ServiceContext;

/**
 * A message handler.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageAccessorImpl implements MessageAccessor<MessageBean>{
	
	/** the builder for message */
	private MessageBuilder builder;
	
	/**
	 * @param builder the builder to set
	 */
	public void setMessageBuilder(MessageBuilder builder){
		this.builder = builder;
	}
	
	/**
	 * @see framework.logics.builder.MessageAccessor#createMessage(int, java.lang.Object[])
	 */
	@Override
	public MessageBean createMessage(int code, Object... args) {
		return new MessageBean(code, args);
	}

	/**
	 * @see framework.logics.builder.MessageAccessor#addMessage(framework.core.message.MessageBean)
	 */
	@Override
	public MessageBean addMessage(MessageBean message) {
		return addMessage(message,Locale.getDefault());
	}
	
	/**
	 * @see framework.logics.builder.MessageAccessor#addMessage(framework.core.message.MessageBean)
	 */
	@Override
	public MessageBean addMessage(MessageBean message,Locale locale) {
		ServiceContext context = ServiceContext.getCurrentInstance();	
		DefinedMessage defined = builder.load(message, locale);	
		ReplyMessage reply = new ReplyMessage();
		reply.setCode(message.getCode());
		reply.setLevel(defined.getLevel().getLevel());
		reply.setMessage(builder.build(defined.getMessage(),message.getDetail()));
		context.addMessage(reply);
		return message;
	}

}
