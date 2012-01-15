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
	
	private boolean topLevel = false;
	
	/**
	 * @return the topLevel
	 */
	public boolean isTopLevel() {
		return topLevel;
	}

	/**
	 * @param topLevel the topLevel to set
	 */
	public void setTopLevel(boolean topLevel) {
		this.topLevel = topLevel;
	}
	
	/**
	 * @see kosmos.framework.core.context.AbstractContainerContext#release()
	 */
	public void release(){
		topLevel = false;
		super.release();		
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
