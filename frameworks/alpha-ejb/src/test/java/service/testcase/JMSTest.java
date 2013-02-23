/**
 * Copyright 2011 the original author
 */
package service.testcase;

import org.junit.Ignore;
import org.junit.Test;

import service.Registry;
import service.ServiceUnit;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Ignore
public class JMSTest extends ServiceUnit{
	
	private JMSTestBean bean(){
		return Registry.getComponentFinder().getBean(JMSTestBean.class.getSimpleName());
	}

	@Test
	public void send(){
		bean().send();
	}
	
	@Test
	public void publish(){
		bean().publish();
	}
}
