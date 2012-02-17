/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import java.util.List;

import kosmos.framework.core.context.MessageContext;
import kosmos.framework.core.message.MessageResult;

/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceContext {
	
	/** the level of call stack */
	private int callStackLevel = 0;
	
	/** the requestId */
	private String requestId = null;
	

	/** the thread local instance*/
	private static ThreadLocal<ServiceContext> instance = new ThreadLocal<ServiceContext>(){
		protected ServiceContext initialValue() {
			return null;
		}
	};

	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(ServiceContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	/**
	 * @return the current context
	 */
	public static ServiceContext getCurrentInstance(){
		return instance.get();
	}
	
	/**
	 * @param requestId
	 */
	public void setRequestId(String requestId){
		this.requestId = requestId;
	}
	
	/**
	 * @return
	 */
	public String getRequestId(){
		return this.requestId;
	}
	
	/**
	 * @return the level of the call stack
	 */
	public int getCallStackLevel(){
		return callStackLevel;
	}
	
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
	 * @return the message context
	 */
	protected MessageContext getMessageContext() {
		return MessageContext.getCurrentInstance();
	}
	
	
	/**
	 * @return the globalMessageList
	 */
	public List<MessageResult> getMessageList(){
		return getMessageContext().getMessageList();
	}
	
	/**
	 * @param message the message to be added to
	 */
	public void addMessage(MessageResult message){
		getMessageContext().addMessage(message);
	}
	
	/**
	 * @return the globalMessageList
	 */
	public MessageResult[] getMessageArray(){
		return getMessageContext().getMessageArray();
	}
	
	/**
	 * Initializes the context.
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);
	}
	
	/**
	 * Releases the context.
	 */
	public void release(){
		callStackLevel = 0;
		requestId = null;		
		setCurrentInstance(null);
	}

}
