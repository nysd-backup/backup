/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import kosmos.framework.core.context.AbstractContainerContext;

/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceContext extends AbstractContainerContext{
	
	private boolean topLevel = true;
	
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
	 * @return the topLevel
	 */
	public boolean isTopLevel() {
		return topLevel;
	}

	/**
	 * @param topLevel the topLevel to set
	 */
	public void setTopLevel(boolean topLevel) {
		this.topLevel = topLevel;
	}
	
	/**
	 * @see kosmos.framework.core.context.AbstractContainerContext#release()
	 */
	public void release(){
		topLevel = true;
		super.release();			
		setCurrentInstance(null);
	}

}
