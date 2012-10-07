/**
 * Copyright 2011 the original author
 */
package service.testcase;

import org.junit.Ignore;
import org.junit.Test;

import service.ServiceUnit;
import alpha.framework.domain.activation.ServiceLocator;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Ignore
public class AsyncTest extends ServiceUnit{
	private AsyncTestBean bean(){
		return ServiceLocator.getService(AsyncTestBean.class.getSimpleName());
	}
	
	@Test
	public void asyncService() throws Exception {
		bean().asyncService();
	}
}
