/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.autonomous;

import java.util.Stack;

import alpha.framework.domain.activation.AbstractEJBComponentFinder;
import alpha.framework.domain.activation.ServiceLocator;
import alpha.framework.domain.transaction.DomainContext;
import alpha.framework.domain.transaction.TxVerifier;


/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class AutonomousTxContext extends DomainContext{

	private Stack<TxScope> transactionScopes = new Stack<TxScope>();;
	
	private boolean failed = false;
	
	
	/**
	 * @see alpha.framework.domain.transaction.DomainContext#setRollbackOnly()
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
	 * @see alpha.framework.domain.transaction.DomainContext#isRollbackOnly()
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
	 * @see alpha.framework.domain.transaction.DomainContext#initialize()
	 */
	@Override
	public void initialize(){
		super.initialize();
		transactionScopes = new Stack<TxScope>();		
		newTransactionScope();
	}
	
	/**
	 * @see alpha.framework.domain.transaction.DomainContext#release()
	 */
	@Override
	public void release(){	
		transactionScopes.clear();
		transactionScopes = null;
		super.release();				
	}
	
	/**
	 * @see alpha.framework.domain.transaction.DomainContext#getTxVerifier()
	 */
	@Override
	protected TxVerifier getTxVerifier() {
		AbstractEJBComponentFinder finder = ServiceLocator.unwrap();
		return finder.getTxVerifier();
	}
}
