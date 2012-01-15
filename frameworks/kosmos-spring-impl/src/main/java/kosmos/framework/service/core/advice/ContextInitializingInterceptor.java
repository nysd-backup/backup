/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.ServiceContextImpl;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ContextInitializingInterceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ContextInitializingInterceptor {
	
	/**
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint invocation) throws Throwable {
		
		ServiceContext context = ServiceContext.getCurrentInstance();
		if(context == null){
			return invokeAtTopLevel(invocation);
		}else {
			return invoke(invocation);
		}
	}
	
	/**
	* Process at the except top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invoke(ProceedingJoinPoint invocation) throws Throwable {
		return proceed(invocation);
	}
	
	/**
	 * Process at the top level.
	 * 
	 * @param ic the context
	 * @return the result
	 * @throws Throwable
	 */
	protected Object invokeAtTopLevel(ProceedingJoinPoint invocation) throws Throwable {
	
		ServiceContextImpl context = new ServiceContextImpl();
		context.initialize();
		
		try{
			return proceed(invocation);	
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
	protected Object proceed(ProceedingJoinPoint invocation) throws Throwable{
		return invocation.proceed();
	}

}
