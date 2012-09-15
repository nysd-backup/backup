/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client.impl;

import java.lang.reflect.Proxy;

import alpha.framework.domain.messaging.client.AbstractMessageProducer;
import alpha.framework.domain.messaging.client.MessageClientFactory;
import alpha.framework.domain.messaging.client.MessagingProperty;


/**
 * The factory to create JMS producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageClientFactoryImpl implements MessageClientFactory{
	
	/** for queue */
	private AbstractMessageProducer queueProducer;
	
	/** for topic */
	private AbstractMessageProducer topicProducer;
	
	/**
	 * @param queueProducer the queueProducer to set
	 */
	public void setQueueProducer(AbstractMessageProducer queueProducer){
		this.queueProducer = queueProducer;
	}
	
	/**
	 * @param topicProducer the topicProducer to set
	 */
	public void setTopicProducer(AbstractMessageProducer topicProducer){
		this.topicProducer = topicProducer;
	}
	
	/**
	 * @see alpha.framework.domain.messaging.client.MessageClientFactory#createPublisher(java.lang.Class, alpha.framework.domain.messaging.client.MessagingProperty)
	 */
	@Override
	public <T> T createPublisher(Class<T> service,MessagingProperty property) {		
		topicProducer.setProperty(property);
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, topicProducer));
	}

	/**
	 * @see alpha.framework.domain.messaging.client.MessageClientFactory#createSender(java.lang.Class, alpha.framework.domain.messaging.client.MessagingProperty)
	 */
	@Override
	public <T> T createSender(Class<T> service,MessagingProperty property) {
		queueProducer.setProperty(property);
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, queueProducer));
	}

}