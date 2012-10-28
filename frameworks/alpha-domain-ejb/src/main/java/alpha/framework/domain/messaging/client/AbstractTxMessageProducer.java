/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

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
public abstract class AbstractTxMessageProducer extends AbstractMessageProducer{

	/**
	 * @see alpha.framework.domain.messaging.client.AbstractMessageProducer#invoke(java.io.Serializable, java.lang.String)
	 */
	@Override
	protected Object invoke(Object parameter, String destinationName,MessagingProperty property) {
		
		ConnectionFactory factory = property.getConnectionFactory();
		if(factory == null){
			throw new IllegalArgumentException("ConnectionFactory is required");
		}
		Destination destination = createDestination(destinationName);
		
		Object data = preProduce(parameter,destination,property);
		
		try{
			produce(factory, data, destination,property);
		}catch(JMSException jmse){
			throw new MessageClientException(jmse);
		}
		return null;
	}
	
	/**
	 * Pre process for produce.
	 * @param data the data
	 * @param destination the destination
	 * @param property the property
	 */
	protected Object preProduce(Object data, Destination destination,MessagingProperty property) {
		return data;
	}
	
	/**
	 * Send the message
	 * @param factory the factory.
	 * @param data the data
	 * @param destination the destination
	 * @param property the property
	 * @throws JMSException
	 */
	protected void produce(ConnectionFactory factory ,Object data, Destination destination,MessagingProperty property) throws JMSException{
	
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
			Message message = createMessage(session,data);
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
	 * Creates the message
	 * @param data the data
	 * @param session session
	 * @return the message
	 */
	protected abstract Message createMessage(Session session,Object data) throws JMSException;
	
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
