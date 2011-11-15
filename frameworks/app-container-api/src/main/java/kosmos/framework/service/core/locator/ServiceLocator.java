/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import java.lang.reflect.InvocationHandler;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.service.ComponentLocator;
import kosmos.framework.api.service.ServiceActivator;
import kosmos.framework.logics.builder.MessageBuilder;
import kosmos.framework.service.core.async.AsyncServiceFactory;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.api.free.QueryFactory;


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
	 * @return the <code>ServiceActivator</code>
	 */
	public abstract ServiceActivator createServiceActivator();
	
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
	 * @return the <code>QueryFactory</code> only called from WEB 
	 */
	public abstract QueryFactory createClientQueryFactory();
	
	/**
	 * @return the <code>AsyncServiceFactory</code>
	 */
	public abstract AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return the <code>AdvancedOrmQueryFactory</code>
	 */
	public abstract AdvancedOrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return the message builder
	 */
	public abstract MessageBuilder createMessageBuilder();	
	
	/**
	 * @return the ServiceContext
	 */
	public abstract ServiceContext createServiceContext();	

	/**
	 * @return the MessageBuilder
	 */
	public static MessageBuilder createDefaultMessageBuilder(){
		return ((ServiceLocator)delegate).createMessageBuilder();
	}
	
	/**
	 * @return the MessageClientFactory
	 */
	public static MessageClientFactory createDefaultMessageClientFactory(){
		return ((ServiceLocator)delegate).createMessageClientFactory();
	}
	
	/**
	 * @return the ServiceActivator
	 */
	public static ServiceActivator createDefaultServiceActivator(){
		return ((ServiceLocator)delegate).createServiceActivator();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultPublisher(){
		return ((ServiceLocator)delegate).createPublisher();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultSender(){
		return ((ServiceLocator)delegate).createSender();
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultQueryFactory(){
		return ((ServiceLocator)delegate).createQueryFactory();
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultClientQueryFactory(){
		return ((ServiceLocator)delegate).createClientQueryFactory();
	}
	
	/**
	 * @return the AdvancedOrmQueryFactory
	 */
	public static AdvancedOrmQueryFactory createDefaultOrmQueryFactory(){
		return ((ServiceLocator)delegate).createOrmQueryFactory();
	}
	
	/**
	 * @return the AsyncServiceFactory
	 */
	public static AsyncServiceFactory createDefaultAsyncServiceFactory(){
		return ((ServiceLocator)delegate).createAsyncServiceFactory();
	}
	
	/**
	 * @return the ServiceContext
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ServiceContext> T createDefaultServiceContext(){
		return (T)((ServiceLocator)delegate).createServiceContext();
	}
		
	
}
