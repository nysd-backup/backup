/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import alpha.framework.domain.transaction.ServiceContext;

/**
 * ServiceContextImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ServiceContextImpl extends ServiceContext{
	
	/**
	 * @see alpha.framework.domain.transaction.ServiceContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	/**
	 * @see alpha.framework.domain.transaction.ServiceContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}

}
