/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.advice.InternalSQLBuilderInterceptor;


/**
 * An advice for the SQL Builder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SQLBuilderInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		return new InternalSQLBuilderInterceptor().around(new InvocationAdapterImpl(ic));
	}

}
