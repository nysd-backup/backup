/**
 * Copyright 2011 the original author
 */
package framework.service.ext.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import framework.service.core.transaction.ServiceContext;

/**
 * The intercepter for border of transaction.
 * 
 * <pre>
 * The method of getting the transaction id depends of EJB container.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractTransactionInterceptor {

	/** the context */
	@Resource
	private SessionContext context;
	
	/**
	 * @param ic　the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Throwable {
		
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
	 * Creates the transaction id from <code>SessionContext</code>
	 * @param context the context
	 * @return　the transaction id
	 */
	protected abstract String getCurrentTransactionId(SessionContext context);
}
