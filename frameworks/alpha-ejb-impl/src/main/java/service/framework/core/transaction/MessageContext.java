/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction;

import java.util.ArrayList;
import java.util.List;

import core.message.MessageResult;

/**
 * The MessageContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageContext {
	
	/** the message */
	private List<MessageResult> messageResult = new ArrayList<MessageResult>();

	private static ThreadLocal<MessageContext> messageContext = new ThreadLocal<MessageContext>();	
	
	/**
	 * @param messageResult the message
	 */
	public MessageContext(List<MessageResult> messageResult){
		release();
		this.messageResult = messageResult;
		messageContext.set(this);
	}

	/**
	 * @return instance
	 */
	public static List<MessageResult> getMessages(){
		MessageContext context = messageContext.get();
		if(context != null){
			List<MessageResult> clone = new ArrayList<MessageResult>(context.messageResult);			
			context.release();
			return clone;
		}
		return null;
	}
	
	/**
	 * Release the context
	 */
	private void release(){
		messageResult = new ArrayList<MessageResult>();
		messageContext.remove();
	}
}
