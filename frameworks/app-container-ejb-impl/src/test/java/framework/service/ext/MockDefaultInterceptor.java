/**
 * Copyright 2011 the original author
 */
package framework.service.ext;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import framework.service.ext.advice.ContextAdapterImpl;

/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MockDefaultInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		return new MockInternalDefaultInterceptor().around(new ContextAdapterImpl(ic));
	}
}
