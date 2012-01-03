/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;


/**
 * The wrapper of the joinPoint.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface InvocationAdapter {
	
	/**
	 * @return the method name
	 */
	public String getMethodName();
	
	/**
	 * @return the declaring type name
	 */
	public String getDeclaringTypeName();
	
	/**
	 * @return the arguments
	 */
	public Object[] getArgs();
	
	/**
	 * @return the target object
	 */
	public Object getThis();
	
	/**
	 * Proceed the context.
	 * 
	 * @return the result
	 */
	public Object proceed() throws Throwable;

}
