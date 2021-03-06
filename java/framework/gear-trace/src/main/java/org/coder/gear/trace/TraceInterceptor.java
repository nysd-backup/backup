/**
 * Copyright 2011 the original author
 */
package org.coder.gear.trace;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;


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
		Tracer.start(method.getDeclaringClass(),method.getName());		
		try{
			return context.proceed();
		}finally {
			Tracer.stop();
		}
	}

}
