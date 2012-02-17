/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import kosmos.framework.service.core.advice.InternalInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * the intercepter to delegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DelegatingInterceptor{
	
	/** the advice */
	private InternalInterceptor advice;
	
	/**
	 * @param advice the advice
	 */
	public void setInternal(InternalInterceptor advice){		
		this.advice = advice;
	}

	/**
	 * @param invocation　the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	public Object around(ProceedingJoinPoint invocation) throws Throwable {
		return advice.around(new InvocationAdapterImpl(invocation));
	}

}
