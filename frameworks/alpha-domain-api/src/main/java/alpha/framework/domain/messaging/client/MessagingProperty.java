/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

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
	
	private Map<String,Object> clientOption = null;
	
	private String destinationPrefix = null;
	
	private String dynamicDestinationName = null;
	
	private String JMSCorrelationID = null;
	
	private String JMSType = null;
	
	private Map<String,Object> JMSProperty = new HashMap<String,Object>();

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
	 * @return the dynamicDestinationName
	 */
	public String getDynamicDestinationName() {
		return dynamicDestinationName;
	}

	/**
	 * @param dynamicDestinationName the dynamicDestinationName to set
	 */
	public void setDynamicDestinationName(String dynamicDestinationName) {
		this.dynamicDestinationName = dynamicDestinationName;
	}


	/**
	 * @param jmsProperty the jmsProperty to set
	 */
	public void addJMSProperty(String key, Object value) {
		this.JMSProperty.put(key, value);
	}

	/**
	 * @return the jMSCorrelationID
	 */
	public String getJMSCorrelationID() {
		return JMSCorrelationID;
	}

	/**
	 * @param jMSCorrelationID the jMSCorrelationID to set
	 */
	public void setJMSCorrelationID(String jMSCorrelationID) {
		JMSCorrelationID = jMSCorrelationID;
	}

	/**
	 * @return the jMSType
	 */
	public String getJMSType() {
		return JMSType;
	}

	/**
	 * @param jMSType the jMSType to set
	 */
	public void setJMSType(String jMSType) {
		JMSType = jMSType;
	}

	/**
	 * @return the jMSProperty
	 */
	public Map<String,Object> getJMSProperty() {
		return JMSProperty;
	}

	/**
	 * @param jMSProperty the jMSProperty to set
	 */
	public void setJMSProperty(Map<String,Object> jMSProperty) {
		JMSProperty = jMSProperty;
	}

	/**
	 * @return the destinationPrefix
	 */
	public String getDestinationPrefix() {
		return destinationPrefix;
	}

	/**
	 * @param destinationPrefix the destinationPrefix to set
	 */
	public void setDestinationPrefix(String destinationPrefix) {
		this.destinationPrefix = destinationPrefix;
	}
	
	/**
	 * @param config the config to create
	 * @return the property
	 */
	public static MessagingProperty createFrom(JMSConfig config){
		MessagingProperty property = new MessagingProperty();
		if(config != null){
			property.setDestinationPrefix(config.destinationPrefix());
			property.setDynamicDestinationName(config.destinationName());
			property.setJMSType(config.jmsType());
			Map<String,Object> prop = new HashMap<String,Object>();
			for(JMSProperty p : config.property()){
				prop.put(p.name(), p.value());
			}
			property.setJMSProperty(prop);
		}
		return property;
	}
	
	/**
	 * Override the property.
	 * @param property the property
	 */
	public void override(MessagingProperty property){
		if(StringUtils.isNotEmpty(property.getDestinationPrefix())){
			setDestinationPrefix(property.getDestinationPrefix());
		}
		if(StringUtils.isNotEmpty(property.getDestinationPrefix())){
			setDestinationPrefix(property.getDestinationPrefix());
		}
		if(property.getConnectionFactory() != null){
			setConnectionFactory(property.getConnectionFactory());
		}
		if(StringUtils.isNotEmpty(property.getJMSCorrelationID())){
			setJMSCorrelationID(property.getJMSCorrelationID());
		}
		if(StringUtils.isNotEmpty(property.getJMSType())){
			setJMSType(property.getJMSType());
		}
		Map<String,Object> props = property.getJMSProperty();
		for(Entry<String,Object> p : props.entrySet()){
			this.JMSProperty.put(p.getKey(), p.getValue());
		}
		
	}
	
	/**
	 * @param key the key
	 * @param value the value
	 */
	public void putClientOption(String key , Object value){
		this.clientOption.put(key, value);
	}

	/**
	 * @return the clientOption
	 */
	public Map<String, Object> getClientOption() {
		return clientOption;
	}

	/**
	 * @param clientOption the clientOption to set
	 */
	public void setClientOption(Map<String, Object> clientOption) {
		this.clientOption = clientOption;
	}

	
}
