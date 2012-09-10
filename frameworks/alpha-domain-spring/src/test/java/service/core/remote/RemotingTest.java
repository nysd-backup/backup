/**
 * Copyright 2011 the original author
 */
package service.core.remote;


import javax.persistence.EntityManager;

import org.junit.Ignore;

import service.test.ServiceUnit;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Ignore
public class RemotingTest extends ServiceUnit{

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * RMI
	 */
//	@Test
//	public void rmi(){
//		RemotingService remote = ServiceLocator.lookup("remoteService");
//		remote.test("aaaag");
//	}
//	
//	/**
//	 * HTTP
//	 */
//	@Test
//	public void http(){
//		RemotingService remote = ServiceLocator.lookup("httpRemoteService");
//		remote.test("aaaag");
//	}
//	
//
//	/**
//	 * JMS
//	 */
//	@Test
//	public void jms(){
//		RemotingService remote = ServiceLocator.lookup("jmsRemoteService");
//		remote.test("aaaag");
//	}
}
