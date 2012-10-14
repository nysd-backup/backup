/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction.autonomous;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.InvocationContext;

import alpha.framework.domain.transaction.DomainContext;
import alpha.framework.domain.transaction.consecutive.ConsecutiveTxInterceptor;


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
	 * @see alpha.framework.domain.transaction.consecutive.ConsecutiveTxInterceptor#invoke(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invoke(InvocationContext ic) throws Throwable {
		AutonomousTxContext context = (AutonomousTxContext)DomainContext.getCurrentInstance();
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
			return proceed(ic);
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
	 * @see alpha.framework.domain.transaction.consecutive.ConsecutiveTxInterceptor#createServiceContext()
	 */
	@Override
	protected DomainContext createServiceContext(){
		return new AutonomousTxContext();
	}

}
