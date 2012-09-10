/**
 * Copyright 2011 the original author
 */
package service.testcase;

import org.junit.Test;

import alpha.framework.domain.activation.ServiceLocator;

import service.ServiceUnit;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */

public class AsyncTest extends ServiceUnit{
	private AsyncTestBean bean(){
		return ServiceLocator.getService(AsyncTestBean.class.getSimpleName());
	}
	
	@Test
	public void asyncService() throws Exception {
		bean().asyncService();
	}
}
