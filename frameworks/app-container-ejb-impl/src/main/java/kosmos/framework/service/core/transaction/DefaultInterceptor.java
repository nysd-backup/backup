/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.service.core.advice.InternalDefaultInterceptor;
import kosmos.framework.service.core.advice.InvocationAdapterImpl;
import kosmos.framework.service.core.exception.ApplicationException;


/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultInterceptor {
	
	@Resource
	private SessionContext context;
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		InternalDefaultInterceptor internal =  new InternalDefaultInterceptor(){
			@Override
			protected BusinessException afterError(Object retValue){
				context.setRollbackOnly();
				return new ApplicationException(null);
			}
		};
		return internal.around(new InvocationAdapterImpl(ic));
	}
}
