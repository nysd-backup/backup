/**
 * Copyright 2011 the original author
 */
package alpha.framework.transaction.autonomous;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.InvocationContext;

import alpha.framework.transaction.TransactionContext;
import alpha.framework.transaction.consecutive.ConsecutiveTxInterceptor;


/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class AutonomousTxInterceptor extends ConsecutiveTxInterceptor {
	
	@Resource
	private EJBContext sessionContext;

	/**
	 * @see alpha.framework.transaction.consecutive.ConsecutiveTxInterceptor#invoke(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invoke(InvocationContext ic) throws Throwable {
		AutonomousTxContext context = (AutonomousTxContext)TransactionContext.getCurrentInstance();
		boolean isTransactionBorder = false;
		TransactionAttribute attribute = ic.getMethod().getDeclaringClass().getAnnotation(TransactionAttribute.class);
				
		if(attribute != null && TransactionAttributeType.REQUIRES_NEW == attribute.value()){
			isTransactionBorder = true;
		}else {
			attribute = ic.getMethod().getAnnotation(TransactionAttribute.class);
			if(attribute != null && TransactionAttributeType.REQUIRES_NEW == attribute.value()){
				isTransactionBorder = true;
			}
		}
		if(isTransactionBorder){
			context.newTransactionScope();
		}
		try{		
			Object retValue = proceed(ic);
			if(isTransactionBorder){
				finishTransactionBorder(retValue);
			}
			return retValue;
		}finally{			
			//Synchronization 			
			if(isTransactionBorder){
				try{
					if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
						sessionContext.setRollbackOnly();				
					}
				}finally{
					context.removeTransactionScope();
				}
			}
		}
	}
	
	/**
	 * Transaction border event.
	 * @param retValue value to return
	 */
	protected void finishTransactionBorder(Object retValue){
		
	}
	
	/**
	 * @see alpha.framework.transaction.consecutive.ConsecutiveTxInterceptor#createServiceContext()
	 */
	@Override
	protected TransactionContext createServiceContext(){
		return new AutonomousTxContext();
	}

}
