/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.activation;

import kosmos.framework.core.dto.InvocationParameter;



/**
 * The internal interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ServiceActivator {

	/**
	 * @param contextInvoker the invoker
	 * @return the result
	 * @throws Throwable the exception
	 */
	public Object activate(InvocationParameter contextInvoker) throws Throwable;
}
