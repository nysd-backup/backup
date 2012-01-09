/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.advice.InternalDefaultInterceptor;
import kosmos.framework.service.core.advice.InvocationAdapter;
import kosmos.framework.service.core.advice.InvocationAdapterImpl;


/**
 * The intercepter for border of transaction.
 * This is exclusive to MockDefaultInterceptor.
 * 
 * @see InternalNestedTransactionInterceptor
 * @author yoshida-n
 * @version	created.
 */
@Deprecated
public abstract class AbstractNestedTransactionInterceptor {
	
	@Resource
	private SessionContext context;
	
	/**
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		
		InternalDefaultInterceptor internal = new InternalDefaultInterceptor(){
			
			@Override
			protected Object proceed(InvocationAdapter ic) throws Throwable {
				
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
		};
		
		return internal.around(new InvocationAdapterImpl(ic));
	}
	
	/**
	 * Creates the transactionId.
	 * 
	 * @param context the SessionContext
	 * @return the transactionId
	 */
	protected abstract String getCurrentTransactionId(SessionContext context);
}
