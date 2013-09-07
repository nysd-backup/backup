/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.rs;

import java.lang.reflect.Proxy;

import javax.ws.rs.client.Client;


/**
 * HttpClientFactoryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class HttpClientBuilder {
	
	private Class<?> serviceType;
	
	private String contextRoot;
	
	private Client client;
	
	/**
	 * Restrict construction .
	 */
	private HttpClientBuilder(){
		
	}
	
	/**
	 * @param serviceType to set
	 * @return self
	 */
	public static HttpClientBuilder builderFor(Class<?> serviceType) {
		HttpClientBuilder builder = new HttpClientBuilder();
		builder.serviceType = serviceType;
		return builder;
	}
	
	/**
	 * @param contextRoot to set 
	 * @return self
	 */
	public HttpClientBuilder contextRoot(String contextRoot){
		this.contextRoot = contextRoot;
		return this;
	}
	
	/**
	 * @param client to set
	 * @return self
	 */
	public HttpClientBuilder client(Client client){
		this.client = client;
		return this;
	}
	

	@SuppressWarnings("unchecked")
	public <T> T build() {
		HttpInvocationHandler handler = new HttpInvocationHandler();
		handler.setContextRoot(contextRoot);
		if(client != null){
			handler.setClient(client);
		}
		return (T) Proxy.newProxyInstance(serviceType.getClassLoader(), new Class[]{serviceType}, handler);
	}

}
