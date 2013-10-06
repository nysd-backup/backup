/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.rs;

import java.lang.reflect.Proxy;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.client.ClientConfig;


/**
 * HttpClientBuilder.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class HttpClientBuilder {
	
	/** type */
	private Class<?> serviceType;

	/** client */
	private Client client;
	
	/**
	 * Restrict construction .
	 */
	private HttpClientBuilder(){
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getClasses().add(MOXyJsonProvider.class);	
		client = ClientBuilder.newBuilder().withConfig(clientConfig).build();
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
	 * @param client to set
	 * @return self
	 */
	public HttpClientBuilder withClient(Client client){
		this.client = client;
		return this;
	}
	
	/**
	 * @return proxy
	 */
	@SuppressWarnings("unchecked")
	public <T> T build(List<String> contextRoot) {
		HttpInvocationHandler handler = new HttpInvocationHandler(contextRoot,client);		
		return (T) Proxy.newProxyInstance(serviceType.getClassLoader(), new Class[]{serviceType}, handler);
	}

}
