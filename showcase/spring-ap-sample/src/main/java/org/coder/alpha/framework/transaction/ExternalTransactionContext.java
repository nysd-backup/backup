/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import org.coder.alpha.message.context.MessageContext;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * ExternalTransactionContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ExternalTransactionContext extends MessageContext{
	
	/**
	 * @see org.coder.alpha.message.context.MessageContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	/**
	 * @see org.coder.alpha.message.context.MessageContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
	}

}
