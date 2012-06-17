/**
 * Copyright 2011 the original author
 */
package service.framework.core.advice;

/**
 * The internal interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface InternalInterceptor {

	/**
	 * @param contextInvoker the invoker
	 * @return the result
	 * @throws Throwable the exception
	 */
	public Object around(InvocationAdapter contextInvoker) throws Throwable;
}
