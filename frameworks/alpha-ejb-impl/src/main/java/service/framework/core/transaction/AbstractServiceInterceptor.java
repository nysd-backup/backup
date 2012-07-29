/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * AbstractServiceInterceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractServiceInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Exception {	
		ServiceContext context = ServiceContext.getCurrentInstance();		
		if(context == null){
			return invokeRoot(ic);
		}else {
			return invoke(ic);
		}
	}
	
	/**
	 * Process at the except top level.
	 * Starts new unit of work if in transaction border.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected abstract Object invokeRoot(InvocationContext ic) throws Exception;
	
	/**
	 * Invokes the service.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected abstract Object invoke(InvocationContext ic) throws Exception;

}
