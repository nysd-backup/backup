/**
 * Copyright 2011 the original author
 */
package service.testcase;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.Stateless;

import service.Registry;
import service.services.AsyncSession;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class AsyncTestBean extends BaseCase{

	public void asyncService() throws InterruptedException, ExecutionException ,CancellationException{
		AsyncSession session = Registry.getComponentFinder().getBean(AsyncSession.class.getSimpleName());
		Future<String> value = session.execute(em);
		String res = value.get();
		assertEquals("aaa", res);
	}
}
