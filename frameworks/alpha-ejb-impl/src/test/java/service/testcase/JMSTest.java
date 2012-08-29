/**
 * Copyright 2011 the original author
 */
package service.testcase;

import org.junit.Test;

import service.ServiceUnit;
import service.framework.core.activation.ServiceLocator;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class JMSTest extends ServiceUnit{
	
	private JMSTestBean bean(){
		return ServiceLocator.getService(JMSTestBean.class.getSimpleName());
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
