/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import kosmos.framework.core.context.AbstractContainerContext;
import kosmos.framework.core.exception.PoorImplementationException;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.core.message.Messages;

/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceContext extends AbstractContainerContext{
	
	/**
	 * @return the current context
	 */
	public static ServiceContext getCurrentInstance(){
		return (ServiceContext)AbstractContainerContext.getCurrentInstance();
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
	 * @param level the message level
	 * @param code the message code 
	 * @param message the message
	 */
	public void addError(MessageResult message){
		if(message.getLevel() < Messages.Level.E.ordinal()){
			throw new PoorImplementationException("invalid message level : level = " + message.getLevel() + " code = " + message.getCode() + " only over error level message is required");
		}		
		globalMessageList.add(message);
	}
	
	/**
	 * @see kosmos.framework.core.context.AbstractContainerContext#release()
	 */
	public void release(){
		super.release();			
		setCurrentInstance(null);
	}
}
