/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * ServiceContextImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ServiceContextImpl extends ServiceContext{
	
	/**
	 * @see service.framework.core.transaction.ServiceContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	/**
	 * @see service.framework.core.transaction.ServiceContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}

}
