/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import alpha.framework.core.message.Message;
import alpha.framework.core.message.MessageLevel;



/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceContext {
	
	private Locale locale;
	
	/** the level of call stack */
	private int callStackLevel = 0;
	
	/** the requestId */
	private String requestId = null;

	/** the messageList */
	private List<Message> messageList = new ArrayList<Message>();
	
	/** flag of error message */
	private boolean failed = false;


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
	 * Adds the messages 
	 * @param message the message
	 */
	public void addMessage(Message message){
		if(message.getLevel() >= MessageLevel.E.ordinal()){
			setRollbackOnly();			
		}		
		messageList.add(message);
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
	 * @return the globalMessageList
	 */
	public List<Message> getMessageList(){
		return messageList;
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
		locale = null;
		callStackLevel = 0;
		requestId = null;	
		messageList = new ArrayList<Message>();
		setCurrentInstance(null);
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * @return failed
	 */
	public boolean isFailed() {
		return failed;
	}

	/**
	 * @param failed the failed to set
	 */
	protected void setFailed(boolean failed){
		this.failed = failed;
	}
	
	/**
	 * Set the current transaction to rolling back.
	 */
	public abstract void setRollbackOnly();
	
	/**
	 * @return true:rollback only
	 */
	public abstract boolean isRollbackOnly();

}
