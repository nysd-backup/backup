/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import javax.interceptor.InvocationContext;

import kosmos.framework.service.core.advice.InvocationAdapter;


/**
 * The wrapper of the joinPoint.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InvocationAdapterImpl implements InvocationAdapter{
	
	private InvocationContext context;
	
	/**
	 * @param joinPoint the joinPoint
	 */
	public InvocationAdapterImpl(InvocationContext context){
		this.context = context;
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return context.getMethod().getName();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getArgs()
	 */
	@Override
	public Object[] getArgs() {
		return context.getParameters();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getThis()
	 */
	@Override
	public Object getThis() {
		return context.getTarget();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#proceed()
	 */
	@Override
	public Object proceed() throws Throwable {
		return context.proceed();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getDeclaringTypeName()
	 */
	@Override
	public String getDeclaringTypeName() {
		return context.getMethod().getDeclaringClass().getName();
	}

}
