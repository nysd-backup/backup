/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.activation.ServiceLocator;


/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ContextControllableTransactionInterceptor {
	
	@Resource
	private SessionContext sessionContext;
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
	
		ServiceContext context = ServiceContext.getCurrentInstance();
		
		if(context == null){
			return invokeAtTopLevel(ic);
		}else {
			return invoke(ic);
		}

	}
	
	/**
	* Process at the except top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invoke(InvocationContext ic) throws Throwable {
		return proceed(ic);
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(InvocationContext ic) throws Throwable {
	
		ServiceContext context = ServiceLocator.createDefaultServiceContext();
		context.initialize();
		try{
			Object returnValue = proceed(ic);	
			if(context.getCurrentUnitOfWork().isRollbackOnly()){
				sessionContext.setRollbackOnly();
			}	
			return returnValue;
		}finally{
			context.release();
		}
	}
	
	/**
	 * Proceed service.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object proceed(InvocationContext ic) throws Throwable{		
		return ic.proceed();
		
	}
}
