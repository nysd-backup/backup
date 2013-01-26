/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.handler.impl;

import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.impl.nio.conn.PoolingClientAsyncConnectionManager;
import org.apache.http.nio.conn.ClientAsyncConnectionManager;
import org.apache.http.nio.reactor.IOReactorException;
import org.coder.alpha.httpclient.config.KeepAliveScope;
import org.coder.alpha.httpclient.config.RequestProperty;
import org.coder.alpha.httpclient.context.HttpClientContext;
import org.coder.alpha.httpclient.handler.AbstractHttpInvocation;
import org.coder.alpha.httpclient.handler.HttpAsyncCallback;
import org.coder.alpha.httpclient.handler.HttpCallback;



/**
 * AsyncHttpClient.
 *
 * @author yoshida-n
 * @version	created.
 */
public class AsyncHttpClient extends AbstractHttpInvocation {
	
	/** total connection */
	private int maxTotal = 100;
	
	/** max connection per route */
	private int defaultMaxPerRoute = 50;
	
	/**
	 * @param defaultMaxPerRoute the defaultMaxPerRoute to set 
	 */
	public void setDefaultMaxPerRoute(int defaultMaxPerRoute){
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}
	
	/**
	 * @param maxTotal the maxTotal to set
	 */
	public void setMaxTotal(int maxTotal){
		this.maxTotal = maxTotal;
	}


	/**
	 * @see org.coder.alpha.httpclient.handler.AbstractHttpInvocation#execute(org.coder.alpha.httpclient.config.RequestProperty, org.apache.http.conn.ClientConnectionManager, org.apache.http.params.HttpParams, org.apache.http.client.methods.HttpRequestBase, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T execute(RequestProperty property,	
			HttpRequestBase method, Class<T> returnType) throws Exception {
		
		DefaultHttpAsyncClient client = createClient(property);
		client.setParams(property.getHttpParams());
		//cookie
		for(Cookie c : property.getHeaderProperty().getCookies()){
			client.getCookieStore().addCookie(c);
		}
		
		//auth
		for(Entry<AuthScope,Credentials> e :property.getCredencials().entrySet()){
			client.getCredentialsProvider().setCredentials(e.getKey(), e.getValue());
		}
				
		//execute and callback	
		Future<HttpResponse> future = client.execute(method, null);		
		HttpCallback callback = property.getExecutionProperty().getHttpCallback();
		if(callback != null){
			if(callback instanceof HttpAsyncCallback){
				((HttpAsyncCallback<T>)callback).setFuture(future,returnType);				
			}else {
				throw new IllegalStateException();
			}
		}
		
		
		return null;
	}
	
	/**
	 * @param property
	 * @return
	 * @throws IOReactorException
	 */
	protected DefaultHttpAsyncClient createClient(RequestProperty property)throws IOReactorException{
		KeepAliveScope scope = property.getConnectionProperty().getKeepAliveScope();
		if(KeepAliveScope.THREAD == scope){
			HttpClientContext context = HttpClientContext.getCurrentInstance();
			if(context == null){
				throw new IllegalStateException("HttpClientContext must be initialized");
			}
			DefaultHttpAsyncClient client = context.getAsynchronousClient();
		
			if(client != null){
				client.getCookieStore().clear();
				client.getCredentialsProvider().clear();
				return client;
			}else {
				client = new DefaultHttpAsyncClient();
				ClientAsyncConnectionManager manager = client.getConnectionManager();
				if(manager instanceof PoolingClientAsyncConnectionManager){
					PoolingClientAsyncConnectionManager am = (PoolingClientAsyncConnectionManager)manager;
					if(defaultMaxPerRoute > 0){
						am.setDefaultMaxPerRoute(defaultMaxPerRoute);
					}
					if(maxTotal > 0){
						am.setMaxTotal(maxTotal);
					}
				}
				context.setAsynchronousClient(client);			
				client.start();
				return client;
			}
		}else {
			throw new IllegalStateException("KeepAliveScope must be THREAD");
		}
	}

}