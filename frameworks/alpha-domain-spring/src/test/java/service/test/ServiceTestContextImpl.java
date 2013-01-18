/**
 * Copyright 2011 the original author
 */
package service.test;

import alpha.framework.domain.transaction.ServiceContext;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceTestContextImpl extends ServiceContext{

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
	 * @see alpha.framework.domain.context.AbstractServiceContext#release()
	 */
	public void release(){
		super.release();		
		this.suppressOptimisticLockError = false;
	}

}
