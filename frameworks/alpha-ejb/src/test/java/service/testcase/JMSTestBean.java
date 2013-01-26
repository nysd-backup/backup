/**
 * Copyright 2011 the original author
 */
package service.testcase;

import javax.ejb.Stateless;

import org.coder.alpha.framework.messaging.client.MessageClientFactoryImpl;
import org.coder.alpha.framework.messaging.client.ObjectMessageProducer;

import service.services.MockService;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class JMSTestBean extends BaseCase{

	public void send(){
		MessageClientFactoryImpl factory = new MessageClientFactoryImpl();
		factory.setQueueProducer(new ObjectMessageProducer());
		MockService mock = factory.createSender(MockService.class,null);
		mock.exec("aaaa");
		mock.exec("bbbb");
	}
	
	public void publish(){
		MessageClientFactoryImpl factory = new MessageClientFactoryImpl();
		factory.setQueueProducer(new ObjectMessageProducer());
		MockService mock = factory.createPublisher(MockService.class,null);
		mock.exec("aaaa");
		mock.exec("bbbb");
	}
}
