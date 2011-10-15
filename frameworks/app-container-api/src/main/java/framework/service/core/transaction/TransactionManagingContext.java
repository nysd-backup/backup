/**
 * Copyright 2011 the original author
 */
package framework.service.core.transaction;

import java.util.LinkedList;

import framework.api.dto.ReplyMessage;
import framework.core.message.MessageLevel;

/**
 * エラーメッセージによるトランザクション管理を行うためのコンテキスト.
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
		try{
			getCurrentUnitOfWork().terminate();
		}finally{
			unitOfWorkStack.pop();
		}
	}
	
	/**
	 * @return the unit of work of current transaction
	 */
	public InternalUnitOfWork getCurrentUnitOfWork(){
		return unitOfWorkStack.peek();
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.core.message.BuildedMessage)
	 */
	@Override
	public void addMessage(ReplyMessage message){
		//エラーレベル以上のメッセージは現在トランザクションをロールバック状態にする
		if( MessageLevel.Error.getLevel() <= MessageLevel.find(message.getLevel()).getLevel()){
			setRollbackOnlyToCurrentTransaction();
		}
		super.addMessage(message);
	}
	

	/**
	 * @see framework.service.core.transaction.ServiceContext#initialize()
	 */
	@Override
	public void initialize(){
		super.initialize();
		// 0 level unit of work
		startUnitOfWork();
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#release()
	 */
	public void release(){
		anyTransactionFailed = false;
		try{
			for(InternalUnitOfWork unit : unitOfWorkStack){
				unit.terminate();
			}
		}finally{
			unitOfWorkStack = new LinkedList<InternalUnitOfWork>();
			super.release();		
		}
	
	}
	
	/** 
	 * @return the internal unit of work
	 */
	protected InternalUnitOfWork createInternalUnitOfWork(){
		return new InternalUnitOfWork();
	}
}
