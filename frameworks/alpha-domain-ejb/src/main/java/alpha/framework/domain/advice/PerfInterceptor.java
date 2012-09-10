/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.advice;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import alpha.framework.domain.advice.InternalPerfInterceptor;




/**
* An intercepter to collect the performance log.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PerfInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		return new InternalPerfInterceptor().around(new InvocationAdapterImpl(ic));
	}

}
