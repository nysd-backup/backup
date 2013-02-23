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
public class AsyncTest extends ServiceUnit{
	private AsyncTestBean bean(){
		return Registry.getComponentFinder().getBean(AsyncTestBean.class.getSimpleName());
	}
	
	@Test
	public void asyncService() throws Exception {
		bean().asyncService();
	}
}
