/**
 * Copyright 2011 the original author
 */
package framework.service.ext.advice;

import javax.interceptor.InvocationContext;

import framework.service.core.advice.ContextAdapter;

/**
 * The wrapper of the joinPoint.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ContextAdapterImpl implements ContextAdapter{
	
	private InvocationContext context;
	
	/**
	 * @param joinPoint the joinPoint
	 */
	public ContextAdapterImpl(InvocationContext context){
		this.context = context;
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return context.getMethod().getName();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getArgs()
	 */
	@Override
	public Object[] getArgs() {
		return context.getParameters();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getThis()
	 */
	@Override
	public Object getThis() {
		return context.getTarget();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#proceed()
	 */
	@Override
	public Object proceed() throws Throwable {
		return context.proceed();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getDeclaringTypeName()
	 */
	@Override
	public String getDeclaringTypeName() {
		return context.getMethod().getDeclaringClass().getName();
	}

}
