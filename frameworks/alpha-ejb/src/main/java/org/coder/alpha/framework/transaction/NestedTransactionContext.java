/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NestedTransactionContext extends TransactionContext{

	private Deque<TransactionScope> transactionScopes = new ArrayDeque<TransactionScope>();
	
	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		getCurrentTransactionScope().setRollbackOnly();
	}
	
	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly(){
		return getCurrentTransactionScope().isRollbackOnly();
	}

	/**
	 * @return current unitOfWorkStack
	 */
	public TransactionScope getCurrentTransactionScope(){
		return transactionScopes.peek();
	}
	
	/**
	 * Start unit of work.
	 */
	public void newTransactionScope(){
		transactionScopes.push(new TransactionScope());
	}
	
	/**
	 * Finish the transaction.
	 */
	public void removeTransactionScope(){
		transactionScopes.pop();
	}

	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#initialize()
	 */
	@Override
	public void initialize(){
		super.initialize();
		transactionScopes = new ArrayDeque<TransactionScope>();		
		newTransactionScope();
	}
	
	/**
	 * @see org.coder.alpha.framework.transaction.TransactionContext#release()
	 */
	@Override
	public void release(){	
		transactionScopes.clear();
		transactionScopes = null;
		super.release();				
	}

}
