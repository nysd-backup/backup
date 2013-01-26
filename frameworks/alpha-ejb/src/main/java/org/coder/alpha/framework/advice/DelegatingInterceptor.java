/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.coder.alpha.framework.advice.InternalInterceptor;
import org.coder.alpha.framework.advice.InvocationAdapter;



/**
 * DelegatingInterceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DelegatingInterceptor implements InvocationHandler{
	
	private final InternalInterceptor interceptor ;
	
	private final Object target;
	
	private final String joinPointMethodName;
	
	/**
	 * @param target the target object
	 * @param interceptor the intercepter
	 * @param joinPointMethodName the method name
	 */
	public DelegatingInterceptor(Object target,InternalInterceptor interceptor,String joinPointMethodName){
		this.interceptor = interceptor;
		this.target = target;
		this.joinPointMethodName = joinPointMethodName;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args)
			throws Throwable {
		InvocationAdapter adapter = new InvocationAdapter() {
			
			@Override
			public Object proceed() throws Throwable {
				try{
					return method.invoke(target, args);
				}catch(InvocationTargetException ite){
					Throwable target = ite.getTargetException();
					throw target;
				}
			}
			
			@Override
			public Object getThis() {
				return target;
			}
			
			@Override
			public String getMethodName() {
				return method.getName();
			}
			
			@Override
			public String getDeclaringTypeName() {
				return method.getDeclaringClass().getName();
			}
			
			@Override
			public Object[] getArgs() {
				return args;
			}
		};
		if(interceptor != null && (adapter.getMethodName().equals(joinPointMethodName) || "*".equals(joinPointMethodName) )){
			return interceptor.around(adapter);
		}else{
			return adapter.proceed();
		}
	}
	
}
