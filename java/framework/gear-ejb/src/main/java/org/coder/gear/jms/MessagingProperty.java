/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.DeliveryMode;

/**
 * MessagingProperty.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class MessagingProperty{

	private String destination = null;
	
	private String correlationID = null;
	
	private String type = null;
	
	private int deliveryMode = DeliveryMode.PERSISTENT;
	
	private int priority = -1;
	
	private long expiration = -1;
	
	private Map<String,String> jmsProperty = new HashMap<String,String>();

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	public void merge(Consumes config){
		if(type == null){
			setJMSType(config.type());
		}
		if(destination == null){
			setDestination(destination);
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
