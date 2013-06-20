/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

import java.util.HashMap;
import java.util.Map;

import javax.jms.DeliveryMode;

/**
 * MessagingProperty.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessagingProperty{

	private String destinationName = null;
	
	private String correlationID = null;
	
	private String type = null;
	
	private int deliveryMode = DeliveryMode.PERSISTENT;
	
	private int priority = -1;
	
	private long expiration = -1;
	
	private Map<String,String> jmsProperty = new HashMap<String,String>();

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
	public void addJmsProperty(String key, String value) {
		this.jmsProperty.put(key, value);
	}

	/**
	 * @return the jMSProperty
	 */
	public Map<String,String> getJmsProperty() {
		return jmsProperty;
	}

	/**
	 * @param config to set
	 */
	public void merge(Produceable config){
		if(type == null){
			setJMSType(config.type());
		}
		if(destinationName == null){
			setDestinationName(destinationName);
		}
		if(priority == -1){
			setJMSPriority(priority);
		}
	}

	
	/**
	 * @param correlationID to set
	 */
	public void setJMSCorrelationID(String correlationID){
		this.correlationID = correlationID;
	}

	
	/**
	 * @return correlationID
	 */
	public String getJMSCorrelationID(){
		return this.correlationID;
	}

	
	/**
	 * @return deliveryMode
	 */
	public int getJMSDeliveryMode(){
		return this.deliveryMode;
	}

	
	/**
	 * @param deliveryMode to set
	 */
	public void setJMSDeliveryMode(int deliveryMode){
		this.deliveryMode = deliveryMode;
	}

	
	/**
	 * @return type
	 */
	public String getJMSType(){
		return this.type;
	}

	
	/**
	 * @param type to set
	 */
	public void setJMSType(String type){
		this.type = type;
	}

	
	/**
	 * @return expiration
	 */
	public long getJMSExpiration(){
		return expiration;
	}

	
	/**
	 * @param expiration to set
	 */
	public void setJMSExpiration(long expiration){
		this.expiration = expiration;		
	}

	
	/**
	 * @return priority
	 */
	public int getJMSPriority(){
		return this.priority;
	}

	
	/**
	 * @param priority to set
	 */
	public void setJMSPriority(int priority){
		this.priority = priority;
	}

}
