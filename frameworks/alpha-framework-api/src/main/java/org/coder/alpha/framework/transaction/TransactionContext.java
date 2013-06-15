/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import java.util.List;


/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class TransactionContext {
	
	/** flag of error message */
	private boolean failed = false;
	
	/** the rollback trigger */
	private RollbackTriggers triggers;
	
	/** the thread local instance*/
	private static ThreadLocal<TransactionContext> instance = new ThreadLocal<TransactionContext>(){
		protected TransactionContext initialValue() {
			return null;
		}
	};

	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(TransactionContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}

	/**
	 * @return the current context
	 */
	public static TransactionContext getCurrentInstance(){
		return instance.get();
	}

	/**
	 * @param trigger the trigger to append 
	 */
	public void acceptRollbackTrigger(RollbackTrigger trigger){		
		if(trigger.isRollbackRequired()){
			setRollbackOnly();
		}
		triggers.append(trigger);
	}	

	/**
	 * @return the globalMessageList
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getRollbackTriggers(){
		return (List<T>)this.triggers.getTriggers();
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
		failed = false;
		triggers = new RollbackTriggers();
		setCurrentInstance(null);
	}

	/**
	 * @return failed
	 */
	public boolean isFailed() {
		return failed;
	}
	
	/**
	 * @param listener the listener to set
	 */
	public void setRollbackTriggerListener(RollbackTriggerListener listener){
		this.triggers.setEventListener(listener);
	}

	/**
	 * @param failed the failed to set
	 */
	protected void setFailed(boolean failed){
		this.failed = failed;
	}
	
	/**
	 * @return true:rollback only
	 */
	public abstract boolean isRollbackOnly();
	
	/**
	 * Set the transaction to rollback.
	 */
	public abstract void setRollbackOnly();

}
