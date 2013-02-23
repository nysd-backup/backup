/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.transaction;

import java.util.Stack;

import org.coder.alpha.framework.transaction.TransactionContext;



/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NestedTransactionContext extends TransactionContext{

	private Stack<TransactionScope> transactionScopes = new Stack<TransactionScope>();;
	
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
		transactionScopes = new Stack<TransactionScope>();		
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