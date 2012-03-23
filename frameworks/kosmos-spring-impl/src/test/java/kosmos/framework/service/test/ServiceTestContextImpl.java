/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.api.PersistenceContext;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceTestContextImpl extends ServiceContext{

	//JPA専用、JPA以外の場合はこれを使用せずヒントを使用する
	private boolean suppressOptimisticLockError = false;
	
	private PersistenceContext context = new PersistenceContext();
	
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
		this.context = new PersistenceContext();
		this.suppressOptimisticLockError = false;
	}

	/**
	 * @return the context
	 */
	public PersistenceContext getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(PersistenceContext context) {
		this.context = context;
	}
}
