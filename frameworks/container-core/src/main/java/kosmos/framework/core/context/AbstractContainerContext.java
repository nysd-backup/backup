/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.context;

import java.util.ArrayList;
import java.util.List;

import kosmos.framework.core.dto.ReplyMessage;


/**
 * The thread-local context
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractContainerContext {

	/** the level of call stack */
	protected int callStackLevel = 0;
	
	/** the list of the messages */
	protected List<ReplyMessage> globalMessageList = new ArrayList<ReplyMessage>();
	
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
	 * @return the globalMessageList
	 */
	public List<ReplyMessage> getMessageList(){
		return this.globalMessageList;
	}
	
	/**
	 * @return the globalMessageList
	 */
	public ReplyMessage[] getMessageArray(){
		 List<ReplyMessage> list = getMessageList();
		 return list.toArray(new ReplyMessage[0]);
	}
	
	/**
	 * Releases the context
	 */
	public void release(){
		globalMessageList.clear();
		callStackLevel = 0;
	}

}
