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

public class AsyncTest extends ServiceUnit{
	private AsyncTestBean bean(){
		return ServiceLocator.getService(AsyncTestBean.class.getSimpleName());
	}
	
	@Test
	public void asyncService() throws Exception {
		bean().asyncService();
	}
}
