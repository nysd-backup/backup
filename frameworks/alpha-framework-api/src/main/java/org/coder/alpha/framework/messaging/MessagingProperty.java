/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.ConnectionFactory;

import org.apache.commons.lang.StringUtils;

/**
 * MessagingProperty.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessagingProperty{

	private ConnectionFactory connectionFactory = null;
	
	private String destinationName = null;
	
	private String jmsCorrelationID = null;
	
	private String jmsType = null;
	
	private int priority = -1;
	
	private Map<String,Object> jmsProperty = new HashMap<String,Object>();

	/**
	 * @return the connectionFactory
	 */
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/**
	 * @param connectionFactory the connectionFactory to set
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	/**
	 * @return the destinationName
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * @param destinationName the destinationName to set
	 */
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}


	/**
	 * @param jmsProperty the jmsProperty to set
	 */
	public void addJmsProperty(String key, Object value) {
		this.jmsProperty.put(key, value);
	}

	/**
	 * @return the jMSCorrelationID
	 */
	public String getJmsCorrelationID() {
		return jmsCorrelationID;
	}

	/**
	 * @param jMSCorrelationID the jMSCorrelationID to set
	 */
	public void setJmsCorrelationID(String jMSCorrelationID) {
		jmsCorrelationID = jMSCorrelationID;
	}

	/**
	 * @return the jMSType
	 */
	public String getJmsType() {
		return jmsType;
	}

	/**
	 * @param jMSType the jMSType to set
	 */
	public void setJmsType(String jMSType) {
		jmsType = jMSType;
	}

	/**
	 * @return the jMSProperty
	 */
	public Map<String,Object> getJmsProperty() {
		return jmsProperty;
	}

	/**
	 * @param jMSProperty the jMSProperty to set
	 */
	public void setJmsProperty(Map<String,Object> jMSProperty) {
		jmsProperty = jMSProperty;
	}

	/**
	 * @param config the config to create
	 * @return the property
	 */
	public static MessagingProperty createFrom(JMSConfig config){
		MessagingProperty property = new MessagingProperty();
		if(config != null){			
			property.setDestinationName(config.destinationName());
			property.setJmsType(config.jmsType());
			property.setPriority(config.priority());
		}
		return property;
	}
	
	/**
	 * Override the property.
	 * @param property the property
	 */
	public void override(MessagingProperty property){
		if(StringUtils.isNotEmpty(property.getDestinationName())){
			setDestinationName(property.getDestinationName());
		}
		if(property.getConnectionFactory() != null){
			setConnectionFactory(property.getConnectionFactory());
		}
		if(StringUtils.isNotEmpty(property.getJmsCorrelationID())){
			setJmsCorrelationID(property.getJmsCorrelationID());
		}
		if(StringUtils.isNotEmpty(property.getJmsType())){
			setJmsType(property.getJmsType());
		}
		Map<String,Object> props = property.getJmsProperty();
		for(Entry<String,Object> p : props.entrySet()){
			this.jmsProperty.put(p.getKey(), p.getValue());
		}
		
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
