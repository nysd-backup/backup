/**
 * Copyright 2011 the original author
 */
package kosmos.framework.test.testcase;

import kosmos.framework.client.service.ServiceCallable;
import kosmos.framework.client.service.ServiceFacade;
import kosmos.framework.client.service.ServiceFacadeInjector;
import kosmos.framework.test.service.TestService;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@ServiceCallable
@ContextConfiguration("/META-INF/oracleAgentNativeApplicationContext.xml")
public class InvokeTest extends ClientUnit{

	@ServiceFacade
	private TestService service;
	
	@Before
	public void before(){
		ServiceFacadeInjector injector = new ServiceFacadeInjector();
		injector.inject(this);
	}
	
	/**
	 * 
	 */
	@Test
	public void error() {
		service.addMessages(3);
		
	}
	
	/**
	 * 
	 */
	@Test
	public void success() {
		service.persist(3);
		
	}
}
