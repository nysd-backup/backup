/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import alpha.framework.domain.transaction.DomainContext;

/**
 * AbstractSessionInterceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractSessionInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {	
		DomainContext context = DomainContext.getCurrentInstance();		
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
	protected abstract Object invokeRoot(InvocationContext ic) throws Throwable;
	
	/**
	 * Invokes the alpha.domain.
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	protected abstract Object invoke(InvocationContext ic) throws Throwable;

}
