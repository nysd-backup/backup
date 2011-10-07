/**
 * Copyright 2011 the original author
 */
package framework.service.ext.advice;

import framework.service.core.advice.ContextAdapter;
import framework.service.core.transaction.ServiceContext;
import framework.service.ext.transaction.NamedInternalUnitOfWork;
import framework.service.ext.transaction.ServiceContextImpl;
import framework.service.ext.transaction.SessionContextAdapter;

/**
 * The intercepter for border of transaction.
 * This is exclusive to AbstractInternalDefaultInterceptor.
 * 
 * <pre>
 * The method of getting the transaction id depends of EJB container.
 * Never to use nether you really need to add message in autonomous transaction.
 * Glassfish v3 can be allowed to use this interceptor now. 
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Deprecated
public class InternalNestedTransactionInterceptor extends InternalDefaultInterceptor{

	/** the adapter */
	private final SessionContextAdapter adapter;
	
	/**
	 * @param adapter the adapter to set
	 */
	public InternalNestedTransactionInterceptor(SessionContextAdapter adapter){
		this.adapter = adapter;
	}
	
	/**
	 * @see framework.service.core.advice.AbstractInternalDefaultInterceptor#proceed(framework.service.core.advice.ContextAdapter)
	 */
	@Override
	protected Object proceed(ContextAdapter ic) throws Throwable {
		
		ServiceContextImpl sc = (ServiceContextImpl)ServiceContext.getCurrentInstance();
		String currentTransactionId = adapter.getCurrentTransactionId();
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
					adapter.setRollbackOnly();
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

}
