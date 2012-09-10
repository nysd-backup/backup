/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

/**
 * MessagingProperty.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessagingProperty{

	private ConnectionFactory connectionFactory = null;
	
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

}
