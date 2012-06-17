/**
 * Copyright 2011 the original author
 */
package service.core.jms;


import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import service.client.messaging.MessageClientFactory;
import service.test.MockService;
import service.test.ServiceUnit;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Ignore
@ContextConfiguration(locations = {"/META-INF/context/oracleAgentApplicationContext.xml","/META-INF/context/jmsApplicationContext.xml"})
public class QueueTest extends ServiceUnit{
	
	@Autowired
	private MessageClientFactory messageClientFactory;

	/**
	 * Queue
	 */
	@Test
	@Rollback(false)
	public void queueSend(){
		
		MockService service = 
			messageClientFactory.createSender(MockService.class);
		
		service.exec("arg2");	
		
	}
	

	/**
	 * Topic
	 */
	@Test
	@Rollback(false)
	public void topicSend(){
		MockService service = messageClientFactory.createPublisher(MockService.class);
		service.exec("TEST");		
	}
}
