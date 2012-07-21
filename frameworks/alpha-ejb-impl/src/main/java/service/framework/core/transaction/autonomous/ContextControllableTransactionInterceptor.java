/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction.autonomous;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.InvocationContext;

import service.framework.core.transaction.ServiceContext;
import service.framework.core.transaction.noautonomous.SimpleInterceptor;


/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ContextControllableTransactionInterceptor extends SimpleInterceptor {
	
	@Resource
	private EJBContext sessionContext;

	/**
	 * @see service.framework.core.transaction.noautonomous.SimpleInterceptor#invoke(javax.interceptor.InvocationContext)
	 */
	@Override
	protected Object invoke(InvocationContext ic) throws Throwable {
		ServiceContextImpl context = (ServiceContextImpl)ServiceContext.getCurrentInstance();
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
			context.startUnitOfWork();
		}
		try{		
			return ic.proceed();
		}finally{			
			//Synchronization 			
			if(isTransactionBorder){
				try{
					if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
						sessionContext.setRollbackOnly();				
					}
				}finally{
					context.endUnitOfWork();
				}
			}
		}
	}
	
	/**
	 * @see service.framework.core.transaction.noautonomous.SimpleInterceptor#createServiceContext()
	 */
	@Override
	protected ServiceContext createServiceContext(){
		return new ServiceContextImpl();
	}

}
