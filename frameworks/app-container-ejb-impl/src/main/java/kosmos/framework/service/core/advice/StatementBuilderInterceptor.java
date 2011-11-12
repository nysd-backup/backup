/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.advice.InternalStatementBuilderInterceptor;


/**
 * An advice for StatementBuilder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StatementBuilderInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		return new InternalStatementBuilderInterceptor().around(new InvocationAdapterImpl(ic));
	}

}
