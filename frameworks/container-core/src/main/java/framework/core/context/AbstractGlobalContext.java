/**
 * Copyright 2011 the original author
 */
package framework.core.context;

import java.util.ArrayList;
import java.util.List;

import framework.api.dto.ReplyMessage;
import framework.core.message.ErrorMessage;

/**
 * The thread-local context
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractGlobalContext {

	/** the level of call stack */
	protected int callStackLevel = 0;
	
	/** the list of the messages */
	protected List<ReplyMessage> globalMessageList = new ArrayList<ReplyMessage>();

	/**
	 * Adds the message to reply to container.
	 * 
	 * @param message the message
	 * @param define the define
	 */
	public abstract void addError(ErrorMessage define, String message);
	
	/**
	 * push call stack.
	 */
	public void pushCallStack(){
		callStackLevel++;
	}
	
	/**
	 * pop call stack.
	 */
	public void popCallStack(){
		callStackLevel--;
	}
	
	/**
	 * @return the level of the call stack
	 */
	public int getCallStackLevel(){
		return callStackLevel;
	}
	

	/**
	 * @return the list of the messages
	 */
	public List<ReplyMessage> getMessageList() {
		return globalMessageList;
	}
	
	/**
	 * Releases the context.
	 */
	protected void release(){
		callStackLevel = 0;
		globalMessageList = new ArrayList<ReplyMessage>();			
	}

}
