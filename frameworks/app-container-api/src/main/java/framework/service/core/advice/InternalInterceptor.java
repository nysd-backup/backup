/**
 * Copyright 2011 the original author
 */
package framework.service.core.advice;

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
	public Object around(ContextAdapter contextInvoker) throws Throwable;
}
