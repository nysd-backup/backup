/**
 * Copyright 2011 the original author
 */
package alpha.framework.advice;

import javax.interceptor.InvocationContext;

import alpha.framework.advice.InvocationAdapter;

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
	 * @see alpha.framework.core.activation.InvocationAdapter#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return context.getMethod().getName();
	}

	/**
	 * @see alpha.framework.core.activation.InvocationAdapter#getArgs()
	 */
	@Override
	public Object[] getArgs() {
		return context.getParameters();
	}

	/**
	 * @see alpha.framework.core.activation.InvocationAdapter#getThis()
	 */
	@Override
	public Object getThis() {
		return context.getTarget();
	}

	/**
	 * @see alpha.framework.core.activation.InvocationAdapter#proceed()
	 */
	@Override
	public Object proceed() throws Throwable {
		return context.proceed();
	}

	/**
	 * @see alpha.framework.core.activation.InvocationAdapter#getDeclaringTypeName()
	 */
	@Override
	public String getDeclaringTypeName() {
		return context.getMethod().getDeclaringClass().getName();
	}

}
