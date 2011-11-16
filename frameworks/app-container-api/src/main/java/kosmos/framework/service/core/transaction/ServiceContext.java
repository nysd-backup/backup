/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import kosmos.framework.core.context.AbstractContainerContext;
import kosmos.framework.core.dto.ReplyMessage;
import kosmos.framework.core.message.ErrorMessage;

/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceContext extends AbstractContainerContext{
	

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
	 * Initializes the context.
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);
	}
	
	/**
	 * Adds the error message.
	 * 
	 * @param define the define 
	 * @param message the message
	 */
	public void addError(ErrorMessage define , String message){
		ReplyMessage reply = new ReplyMessage();
		reply.setCode(define.getCode());
		reply.setLevel(define.getLevel());
		reply.setMessage(message);
		globalMessageList.add(reply);
	}
	
	/**
	 * @see kosmos.framework.core.context.AbstractContainerContext#release()
	 */
	public void release(){
		super.release();			
		setCurrentInstance(null);
	}
}
