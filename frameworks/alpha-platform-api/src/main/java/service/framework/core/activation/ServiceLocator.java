/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;

import java.lang.reflect.InvocationHandler;

import client.sql.free.QueryFactory;
import client.sql.orm.OrmQueryFactory;

import core.activation.ComponentLocator;

import service.client.messaging.MessageClientFactory;
import service.framework.core.async.AsyncServiceFactory;
import service.framework.core.transaction.ServiceContext;



/**
 * A service locator.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceLocator extends ComponentLocator{

	/**
	 * @return the <code>MessageClientFactory</code>
	 */
	public abstract MessageClientFactory createMessageClientFactory();

	/**
	 * @return the JMS publisher
	 */
	public abstract InvocationHandler createPublisher();
	
	/**
	 * @return the JMS sender
	 */
	public abstract InvocationHandler createSender();
	
	/**
	 * @return the <code>QueryFactory</code>
	 */
	public abstract QueryFactory createQueryFactory();
	
	/**
	 * @return the <code>AsyncServiceFactory</code>
	 */
	public abstract AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return the <code>OrmQueryWrapperFactory</code>
	 */
	public abstract OrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return the ServiceContext
	 */
	public abstract ServiceContext createServiceContext();
	
	/**
	 * @return the MessageClientFactory
	 */
	public static MessageClientFactory createDefaultMessageClientFactory(){
		return getDelegate().createMessageClientFactory();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultPublisher(){
		return getDelegate().createPublisher();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultSender(){
		return getDelegate().createSender();
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultQueryFactory(){
		return getDelegate().createQueryFactory();
	}
	
	/**
	 * @return the OrmQueryWrapperFactory
	 */
	public static OrmQueryFactory createDefaultOrmQueryFactory(){
		return getDelegate().createOrmQueryFactory();
	}
	
	/**
	 * @return the AsyncServiceFactory
	 */
	public static AsyncServiceFactory createDefaultAsyncServiceFactory(){
		return getDelegate().createAsyncServiceFactory();
	}
	
	/**
	 * @return the ServiceContext
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ServiceContext> T createDefaultServiceContext(){
		return (T)getDelegate().createServiceContext();
	}		
	
	/**
	 * @return
	 */
	private static ServiceLocator getDelegate(){
		return (ServiceLocator)delegate;
	}
	
}
