/**
 * Copyright 2011 the original author
 */
package framework.service.core.advice;

/**
 * An advice for aop.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Advice {
	
	/**
	 * Be invoked before main process.
	 * @param target　the target instance of weaving
	 * @param methodName the joinpoint
	 * @param args arguments
	 */
	public void before(Object target , String methodName , Object[] args);
	
	
	/**
	 * Be invoked after main process.
	 * @param target　target instance of weaving
	 * @param methodName the joinpoint
	 * @param args the arguments
	 * @param result the execution result
	 */
	public void after(Object target ,String methodName , Object[] args, Object result);

}
