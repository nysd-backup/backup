/**
 * Copyright 2011 the original author
 */
package framework.service.core.transaction;

import framework.api.dto.ReplyMessage;
import framework.core.context.AbstractGlobalContext;

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
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.core.message.BuildedMessage)
	 */
	@Override
	public void addMessage(ReplyMessage message){
		globalMessageList.add(message);
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#release()
	 */
	public void release(){
		super.release();			
		setCurrentInstance(null);
	}
}
