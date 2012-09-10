/**
 * Copyright 2011 the original author
 */
package service.testcase;

import javax.ejb.Stateless;

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
		MockService mock = createSender(MockService.class);
		mock.exec("aaaa");
		mock.exec("bbbb");
	}
	
	public void publish(){
		MockService mock = createPublisher(MockService.class);
		mock.exec("aaaa");
		mock.exec("bbbb");
	}
}
