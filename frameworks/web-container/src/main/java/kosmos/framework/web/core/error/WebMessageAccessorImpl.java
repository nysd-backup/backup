/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.error;

import java.util.Locale;

import kosmos.framework.core.message.ErrorMessage;
import kosmos.framework.logics.builder.MessageAccessor;
import kosmos.framework.logics.builder.MessageBuilder;
import kosmos.framework.web.core.context.WebContext;


/**
 * An engine of message.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class WebMessageAccessorImpl implements MessageAccessor {
	
	private MessageBuilder builder;
	
	/**
	 * @param builder to set
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
		WebContext context = WebContext.getCurrentInstance();	
		String defined = builder.load(message, locale);	
		context.addMessage(message,builder.build(defined,args));
		return message;
	}
	
}
