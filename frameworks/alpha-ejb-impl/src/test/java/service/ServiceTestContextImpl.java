/**
 * Copyright 2011 the original author
 */
package service;

import service.framework.core.transaction.ServiceContextImpl;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceTestContextImpl extends ServiceContextImpl{

	private boolean suppressOptimisticLockError = false;
	
	public void refleshTransactionScope(){
		suppressOptimisticLockError = false;
	}
	
	public void setSuppressOptimisticLockError(){
		suppressOptimisticLockError = true;
	}
	
	public void setValidOptimisticLockError(){
		suppressOptimisticLockError = false;
	}
	
	public boolean isSuppressOptimisticLockError(){
		return suppressOptimisticLockError;
	}
	/**
	 * @see service.framework.core.context.AbstractServiceContext#release()
	 */
	public void release(){
		super.release();
		this.suppressOptimisticLockError = false;
	}
}
