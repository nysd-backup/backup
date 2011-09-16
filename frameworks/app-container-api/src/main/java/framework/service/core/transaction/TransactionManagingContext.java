/**
 * Use is subject to license terms.
 */
package framework.service.core.transaction;

import java.util.LinkedList;
import framework.core.message.BuildedMessage;
import framework.core.message.MessageLevel;

/**
 * エラーメッセージによるトランザクション管理を行うためのコンテキスト.
 *
 * @author yoshida-n
 * @version	created.
 */
public class TransactionManagingContext extends ServiceContext{
	
	/** トランザクション階層 */
	protected LinkedList<InternalUnitOfWork> unitOfWorkStack = new LinkedList<InternalUnitOfWork>();
	
	/**
	 * 開始
	 */
	public void startUnitOfWork(){
		unitOfWorkStack.push(createInternalUnitOfWork());
	}
	
	/**
	 * 終了
	 */
	public void endUnitOfWork(){
		try{
			getCurrentUnitOfWork().terminate();
		}finally{
			unitOfWorkStack.pop();
		}
	}
	
	/**
	 * @return 現在トランザクションの状態
	 */
	public InternalUnitOfWork getCurrentUnitOfWork(){
		return unitOfWorkStack.peek();
	}
	
	/**
	 * @see framework.core.context.AbstractGlobalContext#addMessage(framework.core.message.BuildedMessage)
	 */
	@Override
	public void addMessage(BuildedMessage message){
		//エラーレベル以上のメッセージは現在トランザクションをロールバック状態にする
		if( MessageLevel.Error.getLevel() <= message.getDefined().getLevel().getLevel()){
			getCurrentUnitOfWork().setRollbackOnly();			
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
	 * @return トランザクション内データの作成
	 */
	protected InternalUnitOfWork createInternalUnitOfWork(){
		return new InternalUnitOfWork();
	}
}
