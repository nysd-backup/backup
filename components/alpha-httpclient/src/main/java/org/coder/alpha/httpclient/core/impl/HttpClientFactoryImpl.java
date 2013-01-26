/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.core.impl;

import java.lang.reflect.Proxy;

import org.coder.alpha.httpclient.config.RequestProperty;
import org.coder.alpha.httpclient.core.HttpClientFactory;
import org.coder.alpha.httpclient.core.RequestDispatcher;
import org.coder.alpha.httpclient.handler.HttpInvocation;
import org.coder.alpha.httpclient.handler.impl.AsyncHttpClient;
import org.coder.alpha.httpclient.handler.impl.SyncHttpClient;


/**
 * HttpClientFactoryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class HttpClientFactoryImpl implements HttpClientFactory{
	
	/** Synchronous */
	private HttpInvocation syncHttpClient =new SyncHttpClient();
	
	/** Asynchronous */
	private HttpInvocation asyncHttpClient =new AsyncHttpClient();
	
	/**
	 * @param syncHttpClient the syncHttpClient to set
	 */
	public void setSyncHttpClient(HttpInvocation syncHttpClient){
		this.syncHttpClient = syncHttpClient;
	}
	
	/**
	 * @param asyncHttpClient the asyncHttpClient to set
	 */
	public void setAsyncHttpClient(HttpInvocation asyncHttpClient){
		this.asyncHttpClient = asyncHttpClient;
	}
	
	/**
	 * @see org.coder.alpha.httpclient.core.HttpClientFactory#createService(java.lang.Class, org.coder.alpha.httpclient.config.RequestProperty)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T createService(Class<T> serviceType, RequestProperty property) {
		RequestDispatcher requestDispather 
			= new RequestDispatcher(property,syncHttpClient,asyncHttpClient);		
		return (T) Proxy.newProxyInstance(serviceType.getClassLoader(), new Class[]{serviceType}, requestDispather);
	}

}
