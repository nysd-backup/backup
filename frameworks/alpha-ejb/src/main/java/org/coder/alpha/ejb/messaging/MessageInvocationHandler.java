/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.ejb.messaging;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.bind.annotation.XmlRootElement;

import org.coder.alpha.ejb.registry.ComponentFinder;


/**
 * the message producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageInvocationHandler implements InvocationHandler{
	
	/** the property. */
	private final MessagingProperty property;
	
	/** the context. */
	private final JMSContext context;
	
	/** the componentFinder */
	private final ComponentFinder componentFinder;
	
	public MessageInvocationHandler(MessagingProperty property , JMSContext context , ComponentFinder finder){
		this.property = property;
		this.context = context;
		this.componentFinder = finder;
	}
	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		Consumes config = method.getAnnotation(Consumes.class);
		property.merge(config); 	
		Destination destination = componentFinder.getResource(property.getDestinationName());
		
		//メッセージ作成
		Object data = args[0];
		Message message = createMessage(data);
		setProperties(message);
		
		//送信
		context.createProducer().send(destination, message);
		
		return null;
		
	}
	
	/**
	 * Creates the message.
	 * 
	 * @param data to set
	 * @return message
	 */
	protected Message createMessage(Object data){
		if(data.getClass().getAnnotation(XmlRootElement.class) != null){
			return context.createTextMessage(XmlMarshaller.marshal(data));
		}else if(data instanceof Serializable){
			return context.createObjectMessage((Serializable)data);
		}else {
			throw new IllegalArgumentException();
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
