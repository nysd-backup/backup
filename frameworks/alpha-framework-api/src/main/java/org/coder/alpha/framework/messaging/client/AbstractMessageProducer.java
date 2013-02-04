/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;





/**
 * the message producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractMessageProducer implements InvocationHandler{
	
	/** the selector for JMS destination */
	private DestinationNameResolver destinationNameResolver = new PackageDestinationNameResolver();
	
	/** the hint */
	private MessagingProperty property = null;
	
	public static final String SERVICE_NAME = "alpha.mdb.serviceName";
	
	public static final String METHOD_NAME = "alpha.mdb.methodName";
	
	/**
	 * @param destinationNameResolver the destinationNameResolver to set
	 */
	public void setDestinationNameResolver(DestinationNameResolver destinationNameResolver){
		this.destinationNameResolver = destinationNameResolver;
	}
	
	/**
	 * @param property the property to set
	 */
	public void setProperty(MessagingProperty property){
		this.property = property;
	}
	
	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		JMSConfig config = method.getAnnotation(JMSConfig.class);
		MessagingProperty property = MessagingProperty.createFrom(config);
		property.override(this.property);
		property.addJMSProperty(SERVICE_NAME, method.getDeclaringClass().getName());
		property.addJMSProperty(METHOD_NAME, method.getName());			
		String dst = destinationNameResolver.createDestinationName(method,property);	
		return invoke(args[0],dst,property);
	}
	
	/**
	 * invoke alpha.domain.
	 * @param dto the DTO
	 * @param destinationName the name
	 * @return the result
	 * @throws Throwable　any error
	 */
	protected abstract Object invoke(Object parameter ,String destinationName,MessagingProperty property);
}
