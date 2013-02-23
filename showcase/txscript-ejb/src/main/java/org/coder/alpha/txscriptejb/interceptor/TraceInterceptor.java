/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.interceptor;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.coder.alpha.framework.trace.TraceContext;

/**
 * TraceInterceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
@Interceptor
public class TraceInterceptor {
	
	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object around(InvocationContext context) throws Throwable {
		Method method = context.getMethod();
		TraceContext trace = TraceContext.getCurrentInstance();
		boolean isRoot = trace == null;
		if(isRoot){
			trace = new TraceContext();
			trace.initialize();
		}
		trace.startWatch(method.getDeclaringClass(),method.getName());		
		try{
			return context.proceed();
		}finally {
			trace.stopWatch();
			if(isRoot){
				trace.release();
			}
		}
	}

}
