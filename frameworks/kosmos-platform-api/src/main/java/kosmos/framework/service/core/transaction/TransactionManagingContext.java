/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import java.util.LinkedList;

import kosmos.framework.core.message.MessageLevel;
import kosmos.framework.core.message.MessageResult;


/**
 * The context to manage the transaction status.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class TransactionManagingContext extends ServiceContext{
	
	/** true:any transaction is failed. */
	private boolean anyTransactionFailed = false;
	
	/** the stack of unit of work */
	protected LinkedList<InternalUnitOfWork> unitOfWorkStack = new LinkedList<InternalUnitOfWork>();
	
	/**
	 * set anyTransactionFailed to true. 
	 */
	protected void setAnyTransactionFailed(){
		anyTransactionFailed = true;
	}
	
	/**
	 * @return the anyTransactionFailed
	 */
	public boolean isAnyTransactionFailed(){
		return anyTransactionFailed;
	}
	
	/**
	 * Set the current transaction to rolling back.
	 */
	public void setRollbackOnlyToCurrentTransaction(){
		getCurrentUnitOfWork().setRollbackOnly();
		setAnyTransactionFailed();
	}
	
	/**
	 * start unit of work.
	 */
	public void startUnitOfWork(){
		unitOfWorkStack.push(createInternalUnitOfWork());
	}
	
	/**
	 * end unit of work.
	 */
	public void endUnitOfWork(){
		unitOfWorkStack.pop();
	}
	
	/**
	 * @return the unit of work of current transaction
	 */
	public InternalUnitOfWork getCurrentUnitOfWork(){
		return unitOfWorkStack.peek();
	}

	/**
	 * @see kosmos.framework.service.core.transaction.ServiceContext#addMessage(kosmos.framework.core.message.MessageResult)
	 */
	@Override
	public void addMessage(MessageResult message){
		if(message.getLevel() >= MessageLevel.E.ordinal()){
			setRollbackOnlyToCurrentTransaction();
		}		
		super.addMessage(message);
	}

	/**
	 * @see kosmos.framework.service.core.transaction.ServiceContext#initialize()
	 */
	@Override
	public void initialize(){
		super.initialize();
		// 0 level unit of work
		startUnitOfWork();
	}

	/**
	 * @see kosmos.framework.service.core.transaction.ServiceContext#release()
	 */
	@Override
	public void release(){
		anyTransactionFailed = false;
		unitOfWorkStack = new LinkedList<InternalUnitOfWork>();
		super.release();		
	}
	
	/** 
	 * @return the internal unit of work
	 */
	protected InternalUnitOfWork createInternalUnitOfWork(){
		return new InternalUnitOfWork();
	}

}
