/**
 * Copyright 2011 the original author
 */
package alpha.framework.transaction.autonomous;

import java.util.Stack;

import alpha.framework.registry.ServiceLocator;
import alpha.framework.registry.UnifiedComponentFinder;
import alpha.framework.transaction.TransactionContext;
import alpha.framework.transaction.TxVerifier;


/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class AutonomousTxContext extends TransactionContext{

	private Stack<TxScope> transactionScopes = new Stack<TxScope>();;
	
	private boolean failed = false;
	
	
	/**
	 * @see alpha.framework.transaction.TransactionContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly(){
		failed = true;
		getCurrentTransactionScope().setRollbackOnly();
	}
	
	/**
	 * @return if the transaction is failed.
	 */
	public boolean isAnyTransactionFailed(){
		return failed;
	}
	
	/**
	 * @see alpha.framework.transaction.TransactionContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly(){
		return getCurrentTransactionScope().isRollbackOnly();
	}

	/**
	 * @return current unitOfWorkStack
	 */
	public TxScope getCurrentTransactionScope(){
		return transactionScopes.peek();
	}
	
	/**
	 * Start unit of work.
	 */
	public void newTransactionScope(){
		transactionScopes.push(new TxScope());
	}
	
	/**
	 * Finish the transaction.
	 */
	public void removeTransactionScope(){
		transactionScopes.pop();
	}

	/**
	 * @see alpha.framework.transaction.TransactionContext#initialize()
	 */
	@Override
	public void initialize(){
		super.initialize();
		transactionScopes = new Stack<TxScope>();		
		newTransactionScope();
	}
	
	/**
	 * @see alpha.framework.transaction.TransactionContext#release()
	 */
	@Override
	public void release(){	
		transactionScopes.clear();
		transactionScopes = null;
		super.release();				
	}
	
	/**
	 * @see alpha.framework.transaction.TransactionContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		UnifiedComponentFinder finder = ServiceLocator.getComponentFinder();
		return finder.getTxVerifier();
	}
}
