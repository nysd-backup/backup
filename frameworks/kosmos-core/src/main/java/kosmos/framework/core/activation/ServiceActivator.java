/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.activation;

import kosmos.framework.core.dto.CompositeReply;
import kosmos.framework.core.dto.CompositeRequest;


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
	public CompositeReply activate(CompositeRequest contextInvoker) throws Throwable;
}
