/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import alpha.framework.domain.messaging.client.impl.DestinationNameResolverImpl;




/**
 * the message producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractMessageProducer implements InvocationHandler{
	
	/** the selector for JMS destination */
	private DestinationNameResolver destinationNameResolver = new DestinationNameResolverImpl();
	
	/** the hint */
	private MessagingProperty property = new MessagingProperty();
	
	public static final String SERVICE_NAME = "alpha.mdb.serviceName";
	
	public static final String METHOD_NAME = "alpha.mdb.methodName";
	
	public static final String PARAMETER_TYPE_NAME = "alpha.mdb.parameterTypeName";
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
	 * @return the property
	 */
	protected MessagingProperty getProperty(){
		return property;
	}
	
	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		Serializable[] serial = null;
		if( args == null){
			serial = new Serializable[0];
		}else{
			serial = new Serializable[args.length];
			for(int i = 0 ; i < args.length; i++){
				serial[i] = Serializable.class.cast(args[i]);
			}
		}
		property.addJMSProperty(SERVICE_NAME, method.getDeclaringClass().getName());
		property.addJMSProperty(METHOD_NAME, method.getName());		
		Class<?>[] clss = method.getParameterTypes();
		if( clss != null){			
			String[] names = new String[clss.length];
			for(int i = 0 ; i < names.length; i++){
				names[i] = clss[i].getName();
			}
			property.addJMSProperty(PARAMETER_TYPE_NAME, names);		
		}
	
		String dst = destinationNameResolver.createDestinationName(method,property);	
		return invoke(serial,dst);
	}
	
	/**
	 * invoke alpha.domain.
	 * @param dto the DTO
	 * @param destinationName the name
	 * @return the result
	 * @throws Throwable　any error
	 */
	protected abstract Object invoke(Serializable parameter ,String destinationName) throws Throwable;
}
