/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import core.message.MessageLevel;
import core.message.MessageResult;


/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceContext {
	
	private Locale locale;
	
	/** the level of call stack */
	private int callStackLevel = 0;
	
	/** the requestId */
	private String requestId = null;

	/** true:any transaction is failed. */
	private boolean anyTransactionFailed = false;
	
	private List<MessageResult> messageList = new ArrayList<MessageResult>();

	
	/** the stack of unit of work */
	protected LinkedList<InternalUnitOfWork> unitOfWorkStack = new LinkedList<InternalUnitOfWork>();
	

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
	 * Set the current transaction to rolling back.
	 */
	public void setRollbackOnlyToCurrentTransaction(){
		getCurrentUnitOfWork().setRollbackOnly();
		setAnyTransactionFailed();
	}
	
	/**
	 * start unit of work.
	 */
	public void startUnitOfWork(){
		unitOfWorkStack.push(createInternalUnitOfWork());
	}
	
	/**
	 * end unit of work.
	 */
	public void endUnitOfWork(){
		unitOfWorkStack.pop();
	}
	
	/**
	 * @return the unit of work of current transaction
	 */
	public InternalUnitOfWork getCurrentUnitOfWork(){
		return unitOfWorkStack.peek();
	}

	/**
	 * Adds the messages 
	 * @param message the message
	 */
	public void addMessage(MessageResult message){
		if(message.getLevel() >= MessageLevel.E.ordinal()){
			setRollbackOnlyToCurrentTransaction();
		}		
		addMessageInternal(message);
	}
	
	/**
	 * set anyTransactionFailed to true. 
	 */
	protected void setAnyTransactionFailed(){
		anyTransactionFailed = true;
	}
	
	/**
	 * @return the anyTransactionFailed
	 */
	public boolean isAnyTransactionFailed(){
		return anyTransactionFailed;
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
	public List<MessageResult> getMessageList(){
		return messageList;
	}
	
	/**
	 * Initializes the context.
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);
		startUnitOfWork();
	}
	
	/**
	 * Releases the context.
	 */
	public void release(){
		anyTransactionFailed = false;
		unitOfWorkStack = new LinkedList<InternalUnitOfWork>();
		locale = null;
		callStackLevel = 0;
		requestId = null;	
		messageList.clear();
		messageList = new ArrayList<MessageResult>();
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
	 * @return the internal unit of work
	 */
	protected InternalUnitOfWork createInternalUnitOfWork(){
		return new InternalUnitOfWork();
	}
	
	/**
	 * Adds the message to the context. 
	 * @param message the message
	 */
	protected void addMessageInternal(MessageResult message){
		messageList.add(message);
	}


}
