/**
 * Copyright 2011 the original author
 */
package framework.service.core.transaction;

import framework.api.dto.ReplyMessage;
import framework.core.context.AbstractGlobalContext;
import framework.core.message.ErrorMessage;

/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceContext extends AbstractGlobalContext{
	

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
	 * initialize the context
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#addError(framework.core.message.ErrorMessage, java.lang.String)
	 */
	@Override
	public void addError(ErrorMessage define , String message){
		ReplyMessage reply = new ReplyMessage();
		reply.setCode(define.getCode());
		reply.setLevel(define.getLevel());
		reply.setMessage(message);
		globalMessageList.add(reply);
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#release()
	 */
	public void release(){
		super.release();			
		setCurrentInstance(null);
	}
}
