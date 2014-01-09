/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.sample.javaee7.interceptor;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.coder.gear.trace.MessageContext;

/**
 * SessionInterceptor.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class SessionInterceptor {
	
	@Resource
	private EJBContext sessionContext;
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Exception {	
		MessageContext context = MessageContext.getCurrentInstance();		
		if(context == null){
			return invokeRoot(ic);
		}else {
			return ic.proceed();
		}
	
	}
	
	/**
	 * Process at the top level.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected Object invokeRoot(InvocationContext ic) throws Exception{
		MessageContext context = new MessageContext();
		try{		
			context.initialize();												
			Object returnValue = ic.proceed();
			if(context.isRollbackOnly() && !sessionContext.getRollbackOnly()){
				sessionContext.setRollbackOnly();				
			}							
			return returnValue;	
		}finally{				
			context.release();		
		}
	}

}
