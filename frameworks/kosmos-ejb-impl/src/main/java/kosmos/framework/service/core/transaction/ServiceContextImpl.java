/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import kosmos.framework.core.exception.PoorImplementationException;

/**
 * the context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceContextImpl extends TransactionManagingContext{

	/** ignore optimisic lock error */
	private boolean suppressOptimisticLockError = false;
	
	/**
	 * @param suppressOptimisticLockError
	 */
	public void setSuppressOptimisticLockError(boolean suppressOptimisticLockError){
		this.suppressOptimisticLockError = suppressOptimisticLockError;
	}
	
	/**
	 * @return
	 */
	public boolean isSuppressOptimisticLockError(){
		return suppressOptimisticLockError;
	}
	/**
	 * @see kosmos.framework.service.core.context.AbstractServiceContext#release()
	 */
	public void release(){
		super.release();
		this.suppressOptimisticLockError = false;
	}

	/**
	 * @see kosmos.framework.service.core.transaction.TransactionManagingContext#startUnitOfWork()
	 */
	@Override
	public void startUnitOfWork(){
		//EJBの場合、トランザクション境界の判定ができないため、トランザクション境界に限らず常に1つのNamedInternalUnitOfWorkを使用する。
		//従って自律トランザクションのサービスでは絶対にContextにaddMessageしたりsetRollbatkOnlyToCurrentTransactionは絶対に使用してはならない。
		//代わりにSessionContext#setRollbackOnlyを使用するかExceptionをスローすること	
		if(unitOfWorkStack.size() >= 1){
			throw new PoorImplementationException("cannot start next unitOfwork in EJB");
		}
		unitOfWorkStack.push(createInternalUnitOfWork());
	}
	
}
