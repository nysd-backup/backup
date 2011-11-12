/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.advice.InvocationAdapterImpl;


/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		return new InternalDefaultInterceptor().around(new InvocationAdapterImpl(ic));
	}
}
