/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import alpha.httpclient.config.HttpConfig;
import alpha.httpclient.config.RequestProperty;
import alpha.httpclient.handler.HttpInvocation;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RequestDispatcher implements InvocationHandler{
	
	private RequestProperty requestProperty;
	
	private HttpInvocation httpInvocation;
	
	public void setRequestProperty(RequestProperty requestProperty){
		this.requestProperty = requestProperty;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		HttpConfig config = method.getDeclaringClass().getAnnotation(HttpConfig.class);
		RequestProperty property = null;
		if(config != null){
			property = RequestProperty.create(config);
		}else {
			property = new RequestProperty();
		}
		if(this.requestProperty != null){
			property.override(this.requestProperty);
		}
		//request 
		return httpInvocation.request(property, method, args);
	}

	/**
	 * @param httpInvocation the httpInvocation to set
	 */
	public void setHttpInvocation(HttpInvocation httpInvocation) {
		this.httpInvocation = httpInvocation;
	}

}
