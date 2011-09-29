/**
 * Copyright 2011 the original author
 */
package framework.core.context;

import java.util.ArrayList;
import java.util.List;

import framework.api.dto.ClientRequestBean;
import framework.api.dto.ClientSessionBean;
import framework.core.message.DefinedMessage;

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
	protected List<DefinedMessage> globalMessageList = new ArrayList<DefinedMessage>();
	
	/** the session */
	protected ClientSessionBean clientSession = null;
	
	/** the request */
	protected ClientRequestBean clientRequest = null;
	
	/**
	 * Adds the message to reply to WEB container.
	 * 
	 * @param message the message
	 */
	public abstract void addMessage(DefinedMessage message);
	
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
	 * @return the session
	 */
	public ClientSessionBean getClientSessionBean(){
		return clientSession;
	}
	
	/**
	 * @return the request
	 */
	public ClientRequestBean getClientRequestBean(){
		return clientRequest;
	}
	
	/**
	 * @return the list of the messages
	 */
	public List<DefinedMessage> getMessageList() {
		return globalMessageList;
	}
	
	/**
	 * Releases the context.
	 */
	protected void release(){
		callStackLevel = 0;
		globalMessageList = new ArrayList<DefinedMessage>();
		clientRequest = null;
		clientSession = null;		
	}

}
