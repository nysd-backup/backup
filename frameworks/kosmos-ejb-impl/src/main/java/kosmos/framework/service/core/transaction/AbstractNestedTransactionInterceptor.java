/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.InvocationContext;


/**
 * The intercepter for border of transaction.
 * This is exclusive to MockDefaultInterceptor.
 * 
 * @see InternalNestedTransactionInterceptor
 * @author yoshida-n
 * @version	created.
 */
@Deprecated
public abstract class AbstractNestedTransactionInterceptor extends ContextControllableTransactionInterceptor{
	
	@Resource
	private SessionContext context;
	
	/**
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	@Override
	protected Object proceed(InvocationContext ic) throws Throwable {
		
		ServiceContextImpl sc = (ServiceContextImpl)ServiceContext.getCurrentInstance();
		String currentTransactionId = getCurrentTransactionId(context);
		NamedInternalUnitOfWork upperTransaction = (NamedInternalUnitOfWork)sc.getCurrentUnitOfWork();
		boolean newTransaction = upperTransaction == null || !currentTransactionId.equals(upperTransaction.getTransactionId());

		//トランザクション境界の場合新規作業単位に移行
		if(newTransaction){
			sc.startUnitOfWork();
			NamedInternalUnitOfWork current = (NamedInternalUnitOfWork)sc.getCurrentUnitOfWork();
			current.setTransactionId(currentTransactionId);
		}
		try{											
			Object retVal = ic.proceed();			

			//トランザクション境界の場合、コンテナにロールバック通知
			if(newTransaction){				
				if( sc.getCurrentUnitOfWork().isRollbackOnly()){
					context.setRollbackOnly();
				}				
			}
			return retVal;
		}finally {
			//トランザクション境界の場合、上位の作業単位に戻す
			if(newTransaction){			
				sc.endUnitOfWork();
			}
		}
	}

	
	/**
	 * Creates the transactionId.
	 * 
	 * @param context the SessionContext
	 * @return the transactionId
	 */
	protected abstract String getCurrentTransactionId(SessionContext context);
}
