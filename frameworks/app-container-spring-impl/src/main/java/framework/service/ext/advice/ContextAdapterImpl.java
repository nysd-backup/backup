/**
 * Copyright 2011 the original author
 */
package framework.service.ext.advice;

import org.aspectj.lang.ProceedingJoinPoint;

import framework.service.core.advice.ContextAdapter;

/**
 * The wrapper of the joinPoint.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ContextAdapterImpl implements ContextAdapter{
	
	private ProceedingJoinPoint joinPoint;
	
	/**
	 * @param joinPoint the joinPoint
	 */
	public ContextAdapterImpl(ProceedingJoinPoint joinPoint){
		this.joinPoint = joinPoint;
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return joinPoint.getSignature().getName();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getArgs()
	 */
	@Override
	public Object[] getArgs() {
		return joinPoint.getArgs();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getThis()
	 */
	@Override
	public Object getThis() {
		return joinPoint.getThis();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#proceed()
	 */
	@Override
	public Object proceed() throws Throwable{
		return joinPoint.proceed();
	}

	/**
	 * @see framework.service.core.advice.ContextAdapter#getDeclaringTypeName()
	 */
	@Override
	public String getDeclaringTypeName() {
		return joinPoint.getSignature().getDeclaringTypeName();
	}

}
