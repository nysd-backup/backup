/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.advice.InvocationAdapterImpl;


/**
 * The intercepter for border of transaction.
 * This is exclusive to MockDefaultInterceptor.
 * 
 * @see InternalNestedTransactionInterceptor
 * @author yoshida-n
 * @version	created.
 */
@Deprecated
public abstract class AbstractNestedTransactionInterceptor {
	
	@Resource
	private SessionContext context;
	
	/**
	 * @param ic
	 * @return
	 * @throws Throwable
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		InternalNestedTransactionInterceptor internal = new InternalNestedTransactionInterceptor(createAdapter(context));
		return internal.around(new InvocationAdapterImpl(ic));
	}
	
	/**
	 * Creates the adapter.
	 * 
	 * @param context the SessionContext
	 * @return the adapter
	 */
	protected abstract SessionContextAdapter createAdapter(SessionContext context);
}
