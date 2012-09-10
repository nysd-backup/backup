/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.autonomous;

import java.util.Stack;

import alpha.framework.domain.transaction.ServiceContext;




/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceContextImpl extends ServiceContext{

	private Stack<TransactionScope> transactionScopes = new Stack<TransactionScope>();;
	
	/**
	 * @see alpha.framework.domain.transaction.ServiceContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		setFailed(true);
		getCurrentTransactionScope().setRollbackOnly();
	}
	
	/**
	 * @see alpha.framework.domain.transaction.ServiceContext#isRollbackOnly()
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
	 * @see alpha.framework.domain.transaction.ServiceContext#initialize()
	 */
	@Override
	public void initialize(){
		super.initialize();
		transactionScopes = new Stack<TransactionScope>();		
		newTransactionScope();
	}
	
	/**
	 * @see alpha.framework.domain.transaction.ServiceContext#release()
	 */
	@Override
	public void release(){	
		transactionScopes.clear();
		transactionScopes = null;
		super.release();				
	}
}
