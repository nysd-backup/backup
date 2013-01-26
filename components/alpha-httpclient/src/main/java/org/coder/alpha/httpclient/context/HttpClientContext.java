/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.context;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;


/**
 * HttpClientContext.
 *
 * @author yoshida-n
 * @version	created.
 */
public class HttpClientContext {
	
	/** thread local */
	private static ThreadLocal<HttpClientContext> httpClientContext  
		= new ThreadLocal<HttpClientContext>();
	
	/** synchronous client */
	private DefaultHttpClient synchronousClient = null;
	
	/** asynchronous client */
	private DefaultHttpAsyncClient asynchronousClient = null;

	/**
	 * @return the synchronousClient
	 */
	public DefaultHttpClient getSynchronousClient() {
		return synchronousClient;
	}

	/**
	 * @param synchronousClient the synchronousClient to set
	 */
	public void setSynchronousClient(DefaultHttpClient synchronousClient) {
		this.synchronousClient = synchronousClient;
	}

	/**
	 * @return the asynchronousClient
	 */
	public DefaultHttpAsyncClient getAsynchronousClient() {
		return asynchronousClient;
	}

	/**
	 * @param asynchronousClient the asynchronousClient to set
	 */
	public void setAsynchronousClient(DefaultHttpAsyncClient asynchronousClient) {
		this.asynchronousClient = asynchronousClient;
	}

	/**
	 * @return the httpClientContext
	 */
	public static HttpClientContext getCurrentInstance() {
		return httpClientContext.get();
	}

	public void initialize(){
		release();
		httpClientContext.set(this);
	}
	
	public void release(){
		try{
			if(synchronousClient != null){
				synchronousClient.getConnectionManager().shutdown();
				synchronousClient = null;
			}
		}finally{
			try{
				if(asynchronousClient != null){
					asynchronousClient.getConnectionManager().shutdown();
					asynchronousClient = null;
				}
			}catch(Exception e){
				throw new IllegalStateException(e);
			}finally{
				httpClientContext.remove();
			}
		}

	}
}

