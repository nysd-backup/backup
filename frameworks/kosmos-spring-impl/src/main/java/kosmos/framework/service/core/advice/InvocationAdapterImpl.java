/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;

import kosmos.framework.service.core.advice.InvocationAdapter;

import org.aspectj.lang.ProceedingJoinPoint;


/**
 * The wrapper of the joinPoint.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InvocationAdapterImpl implements InvocationAdapter{
	
	private ProceedingJoinPoint joinPoint;
	
	/**
	 * @param joinPoint the joinPoint
	 */
	public InvocationAdapterImpl(ProceedingJoinPoint joinPoint){
		this.joinPoint = joinPoint;
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return joinPoint.getSignature().getName();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getArgs()
	 */
	@Override
	public Object[] getArgs() {
		return joinPoint.getArgs();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getThis()
	 */
	@Override
	public Object getThis() {
		return joinPoint.getThis();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#proceed()
	 */
	@Override
	public Object proceed() throws Throwable{
		return joinPoint.proceed();
	}

	/**
	 * @see kosmos.framework.service.core.advice.InvocationAdapter#getDeclaringTypeName()
	 */
	@Override
	public String getDeclaringTypeName() {
		return joinPoint.getSignature().getDeclaringTypeName();
	}

}
