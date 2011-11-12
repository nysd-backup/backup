/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import kosmos.framework.service.core.advice.InvocationAdapter;
import kosmos.framework.service.core.transaction.TransactionManagingContext;

/**
 * The end-point of service.
 * 
 * <pre>
 * Creates the context and invocke service.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class ServiceFrontEnd{

	/**
	 * Invokes the service.
	 * 
	 * @param contextInvoker
	 * @return the value to return
	 * @throws Throwable
	 */
	public Object invoke(InvocationAdapter contextInvoker) throws Throwable {
		TransactionManagingContext context = ServiceLocator.createContainerContext();
		context.initialize();
		try{
			return contextInvoker.proceed();
		}finally{
			context.release();
		}
	}

}
