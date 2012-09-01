/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
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
	 * @see service.client.messaging.AbstractMessageProducer#invoke(java.io.Serializable, java.lang.String)
	 */
	@Override
	protected Object invoke(Serializable parameter, String destinationName)
			throws Throwable {
		
		MessagingProperty property = getProperty();
		ConnectionFactory factory = property.getConnectionFactory();
		if(factory == null){
			throw new IllegalArgumentException("ConnectionFactory is required");
		}
		Destination destination = createDestination(destinationName);
		sendMessage(factory, parameter, destination,property);
		return null;
	}
	
	/**
	 * Send the message
	 * @param factory the factory.
	 * @param data the data
	 * @param destination the destination
	 * @param property the property
	 * @throws JMSException
	 */
	protected void sendMessage(ConnectionFactory factory ,Serializable data, Destination destination,MessagingProperty property) throws JMSException{
	
		Connection connection = null;
		Session session = null;		
		MessageProducer sender = null;
		try {
			// コネクションを作成
			connection = factory.createConnection();
	
			// セッションを作成
			// グローバルトランザクション固定
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// MessageProducerを作成
			sender = session.createProducer(destination);			
			// メッセージを作成
			Message message = session.createObjectMessage(data);
			JMSUtils.setPropertyAndHeader(property, message);			
			connection.start();
			sender.send(message);			
		} finally {
			// 接続を切断
			if (sender != null) {
				sender.close();
			}
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				//XAコネクションファクトリ経由のコネクションであればクローズ可能
				connection.close();
			}
			
		}
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
