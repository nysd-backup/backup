/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * AbstractTxInterceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractTxInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Exception {	
		DomainContext context = DomainContext.getCurrentInstance();		
		try{
			if(context == null){
				return invokeRoot(ic);
			}else {
				return invoke(ic);
			}
		}catch(Throwable t){
			if(t instanceof Error ){
				throw (Error)t;
			}else {
				throw (Exception)t;
			}
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
