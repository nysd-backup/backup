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
	public Object around(InvocationContext ic) throws Throwable {	
		ServiceContext context = ServiceContext.getCurrentInstance();		
		if(context == null){
			return invokeFirst(ic);
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
	protected abstract Object invokeFirst(InvocationContext ic) throws Throwable;
	
	/**
	 * Invokes the service.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected abstract Object invoke(InvocationContext ic) throws Throwable;

}
