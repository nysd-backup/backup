/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.jms;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * the message producer.
 *
 * @author	yoshida-n
 * @version	1.0
 */
public class MessageInvocationHandler implements InvocationHandler{
	
	/** the property. */
	private final MessagingProperty property;
	
	/** the context. */
	private final ConnectionFactory factory;
	
	/**
	 * @param property to set
	 * @param factory to set
	 */
	public MessageInvocationHandler(MessagingProperty property , ConnectionFactory factory){
		this.property = property;
		this.factory = factory;
	}
	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		Consumes config = method.getAnnotation(Consumes.class);
		property.merge(config); 	
		Destination destination = resolveDestination(property.getDestination());
		
		//送信
		try(JMSContext context = factory.createContext()){
			Message message = createMessage(context,args[0]);
			setProperties(message);
			context.createProducer().send(destination, message);
		};
		return null;
		
	}
	
	/**
	 * @param destinationName to resolve
	 * @return destination
	 */
	protected Destination resolveDestination(String destinationName ){
		try{
			InitialContext context = new InitialContext();
			Destination destination = Destination.class.cast(context.lookup(destinationName));
			context.close();
			return destination;
		}catch(NamingException ne){
			throw new JMSRuntimeException(ne.getMessage(),ne.getMessage(),ne);
		}
		
	}
	
	/**
	 * Creates the message.
	 * 
	 * @param data to set
	 * @return message
	 */
	protected Message createMessage(JMSContext context ,Object data){
		if(data.getClass().getAnnotation(XmlRootElement.class) != null){
			return context.createTextMessage(XmlMarshaller.marshal(data));
		}else if(data instanceof Serializable){
			return context.createObjectMessage((Serializable)data);
		}else {
			throw new JMSRuntimeException("invalid data type");
		}
	}
	
	/**
	 * Set the property.
	 * 
	 * @param message to set 
	 * @throws JMSException exception
	 */
	protected void setProperties(Message message) throws JMSException{
		if( property.getJMSCorrelationID() != null){
			message.setJMSCorrelationID(property.getJMSCorrelationID());
		}
		if( property.getJMSDeliveryMode() != DeliveryMode.PERSISTENT){
			message.setJMSDeliveryMode(property.getJMSDeliveryMode());
		}
		if( property.getJMSPriority() > 0){
			message.setJMSPriority(property.getJMSPriority());
		}
		if( property.getJMSType() != null){
			message.setJMSType(property.getJMSType());
		}
		if( property.getJMSExpiration() > 0 ){
			message.setJMSExpiration(property.getJMSExpiration());
		}
		for(Map.Entry<String, String> e : property.getJmsProperty().entrySet()){
			message.setStringProperty(e.getKey(), e.getValue());
		}
		
	}

}
