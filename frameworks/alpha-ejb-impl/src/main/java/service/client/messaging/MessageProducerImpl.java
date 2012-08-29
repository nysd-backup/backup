/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * The JMS producer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageProducerImpl extends AbstractMessageProducer{
	
	public static final String CONNECTION_FACTORY = "alpha.messaging.factory";

	/**
	 * @see service.client.messaging.AbstractMessageProducer#invoke(service.client.messaging.InvocationParameter, java.lang.String)
	 */
	@Override
	protected Object invoke(InvocationParameter dto, String destinationName)
			throws Throwable {
		
		MessagingProperty property = getProperty();
		if(!(property instanceof EJBMessagingProperty)){
			throw new IllegalStateException("EJBMessagingProperty is required");
		}
		
		EJBMessagingProperty ejbProp = EJBMessagingProperty.class.cast(property);
		ConnectionFactory factory = ejbProp.getConnectionFactory();
		if(factory == null){
			throw new IllegalArgumentException("ConnectionFactory is required");
		}
		Destination destination = createDestination(destinationName);
		JmsUtils.sendMessage(factory, dto, destination,ejbProp);
		return null;
	}
	
	/**
	 * Creates the destination.
	 * 
	 * @param destinationName the name of destination
	 * @return the destination
	 */
	protected Destination createDestination(String destinationName){
		Destination destination = lookup(destinationName);
		return destination;
	}
	
	/**
	 * @param resourceName the resourceName
	 * @return object
	 */
	@SuppressWarnings("unchecked")
	protected <T> T lookup(String resourceName){
		InitialContext context = null;
		try {
			context = new InitialContext();
			return (T)context.lookup(resourceName);
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}finally {
			if(context != null){
				try {
					context.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
