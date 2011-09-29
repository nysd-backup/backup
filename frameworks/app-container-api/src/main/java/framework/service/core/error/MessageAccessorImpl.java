/**
 * Copyright 2011 the original author
 */
package framework.service.core.error;

import framework.api.dto.ClientSessionBean;
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
		ServiceContext context = ServiceContext.getCurrentInstance();
		ClientSessionBean bean = context.getClientSessionBean();		
		DefinedMessage defined = builder.load(message, bean.getLocale());		
		context.addMessage(defined);
		return message;
	}

}
