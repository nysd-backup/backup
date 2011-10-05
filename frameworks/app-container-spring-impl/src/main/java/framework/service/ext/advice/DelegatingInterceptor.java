/**
 * Copyright 2011 the original author
 */
package framework.service.ext.advice;

import org.aspectj.lang.ProceedingJoinPoint;

import framework.service.core.advice.Advice;

/**
 * the intercepter to delegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DelegatingInterceptor{
	
	/** the advice */
	private Advice advice;
	
	/**
	 * @param advice the advice
	 */
	public void setAdvice(Advice advice){		
		this.advice = advice;
	}

	/**
	 * @param invocation　the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	public Object invoke(ProceedingJoinPoint invocation) throws Throwable {
		advice.before(invocation.getTarget(), invocation.getSignature().getName(),invocation.getArgs());
		
		Object value = invocation.proceed();
		
		advice.after(invocation.getThis(), invocation.getSignature().getName(),invocation.getArgs(),value);
		
		return value;
	}

}
