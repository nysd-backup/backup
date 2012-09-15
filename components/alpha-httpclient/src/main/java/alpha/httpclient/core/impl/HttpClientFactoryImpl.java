/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.core.impl;

import java.lang.reflect.Proxy;

import alpha.httpclient.config.RequestProperty;
import alpha.httpclient.core.HttpClientFactory;
import alpha.httpclient.core.RequestDispatcher;
import alpha.httpclient.handler.HttpInvocation;
import alpha.httpclient.handler.impl.SyncHttpClient;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class HttpClientFactoryImpl implements HttpClientFactory{
	
	private HttpInvocation httpInvocation =new SyncHttpClient();
	
	public void setHttpInvocation(HttpInvocation httpInvocation){
		this.httpInvocation = httpInvocation;
	}
	
	/**
	 * @see alpha.httpclient.core.HttpClientFactory#createService(java.lang.Class, alpha.httpclient.config.RequestProperty)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T createService(Class<T> serviceType, RequestProperty property) {
		RequestDispatcher requestDispather = new RequestDispatcher();
		requestDispather.setHttpInvocation(httpInvocation);
		requestDispather.setRequestProperty(property);
		return (T) Proxy.newProxyInstance(serviceType.getClassLoader(), new Class[]{serviceType}, requestDispather);
	}

}
