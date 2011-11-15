/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import kosmos.framework.service.core.transaction.ServiceContextImpl;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceTestContextImpl extends ServiceContextImpl{

	private boolean suppressOptimisticLockError = false;
	
	public void refleshTransactionScope(){
		super.endUnitOfWork();
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
	 * @see kosmos.framework.service.core.context.AbstractServiceContext#release()
	 */
	public void release(){
		super.release();
		this.suppressOptimisticLockError = false;
	}
}