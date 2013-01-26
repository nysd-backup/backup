/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.handler;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.coder.alpha.httpclient.core.ResponseHandler;


/**
 * HttpAsyncCallback.
 *
 * @author yoshida-n
 * @version	created.
 */
public class HttpAsyncCallback<T> implements HttpCallback{
	
	private Future<HttpResponse> future;
	
	private Class<T> entityClass;
	
	public void setFuture(Future<HttpResponse> future,Class<T> entityClass){
		this.future = future;
		this.entityClass = entityClass;
	}

	/**
	 * @see org.coder.alpha.httpclient.handler.HttpCallback#callback(org.apache.http.HttpResponse)
	 */
	@Override
	public void callback(HttpResponse response) {
		
	}

	/**
	 * @see java.util.concurrent.Future#cancel
	 */
	public boolean cancel(boolean mayInterruptIfRunning) {
		return future.cancel(mayInterruptIfRunning);
	}

	/**
	 * @see java.util.concurrent.Future#isCancelled
	 */
	public boolean isCancelled() {
		return future.isCancelled();
	}

	/**
	 * @see java.util.concurrent.Future#isDone
	 */
	public boolean isDone() {
		return future.isDone();
	}

	/**
	 * @see java.util.concurrent.Future#get
	 */
	public T get() {
		HttpResponse response;
		try {
			response = future.get();		
			T retValue = ResponseHandler.getEntity(response, entityClass);
			callback(response);				
			return retValue;
		} catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * @see java.util.concurrent.Future#get(long,TimeUnit)
	 */
	public T get(long timeout, TimeUnit unit) {
		HttpResponse response;
		try {
			response = future.get(timeout, unit);	
			T retValue = ResponseHandler.getEntity(response, entityClass);
			callback(response);				
			return retValue;
		} catch(Exception e){
			throw new IllegalStateException(e);
		}
	}

}
