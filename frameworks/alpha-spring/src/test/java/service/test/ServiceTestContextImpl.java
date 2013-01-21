/**
 * Copyright 2011 the original author
 */
package service.test;

import alpha.framework.transaction.ExternalTransactionContext;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceTestContextImpl extends ExternalTransactionContext{

	//JPA専用、JPA以外の場合はこれを使用せずヒントを使用する
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
	 * @see alpha.framework.context.AbstractServiceContext#release()
	 */
	public void release(){
		super.release();		
		this.suppressOptimisticLockError = false;
	}

}
