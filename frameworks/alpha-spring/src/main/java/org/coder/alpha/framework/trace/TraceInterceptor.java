/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.trace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.coder.alpha.framework.trace.TraceContext;

/**
 * An intercepter to collect the performance log.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class TraceInterceptor {

	/**
	 * Watch trace.
	 * @param invocation the invocation
	 * @return the result
	 * @throws Throwable the exception
	 */
	public Object around(ProceedingJoinPoint invocation) throws Throwable {

		Signature signature = invocation.getSignature();
		TraceContext trace = TraceContext.getCurrentInstance();
		boolean isRoot = trace == null;
		if(isRoot){
			trace = new TraceContext();
			trace.initialize();
		}
		trace.startWatch(signature.getDeclaringType(),signature.getName());		
		try{
			return invocation.proceed();
		}finally {
			trace.stopWatch();
			if(isRoot){
				trace.release();
			}
		}

	}
}
