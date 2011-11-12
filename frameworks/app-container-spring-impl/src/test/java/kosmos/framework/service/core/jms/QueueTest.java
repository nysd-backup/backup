/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.jms;

import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.test.MockService;
import kosmos.framework.service.test.ServiceUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;


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
	private MessageClientFactory msf;

	/**
	 * Queue
	 */
	@Test
	@Rollback(false)
	public void queueSend(){
		MockService service = msf.createSender(MockService.class);
		service.exec("TEST");		
	}
	

	/**
	 * Topic
	 */
	@Test
	@Rollback(false)
	public void topicSend(){
		MockService service = msf.createPublisher(MockService.class);
		service.exec("TEST");		
	}
}
