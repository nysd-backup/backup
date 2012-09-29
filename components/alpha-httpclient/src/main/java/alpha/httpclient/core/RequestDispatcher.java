/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import alpha.httpclient.config.Asynchronous;
import alpha.httpclient.config.ConnectionConfig;
import alpha.httpclient.config.ProxyConfig;
import alpha.httpclient.config.RequestProperty;
import alpha.httpclient.config.Schemes;
import alpha.httpclient.handler.HttpInvocation;

/**
 * Dispathcer.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RequestDispatcher implements InvocationHandler{
	
	private final RequestProperty requestProperty;
	
	private final HttpInvocation syncHttpClient;
	
	private final HttpInvocation asyncHttpClient;
	
	/**
	 * @param property
	 * @param sync
	 * @param async
	 */
	public RequestDispatcher(RequestProperty property,HttpInvocation sync, HttpInvocation async){
		this.requestProperty = property;
		this.syncHttpClient = sync;
		this.asyncHttpClient = async;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Asynchronous async = method.getAnnotation(Asynchronous.class);
		Schemes schemes = method.getAnnotation(Schemes.class);
		ConnectionConfig con = method.getAnnotation(ConnectionConfig.class);
		ProxyConfig proxyCon = method.getAnnotation(ProxyConfig.class);
		RequestProperty property = RequestProperty.create(con,schemes,async,proxyCon);		
		if(this.requestProperty != null){
			property.override(this.requestProperty);
		}
		if(property.getExecutionProperty().isAsynchronous()){
			return asyncHttpClient.request(property, method, args);
		}else{
			return syncHttpClient.request(property, method, args);
		}
	}

}
