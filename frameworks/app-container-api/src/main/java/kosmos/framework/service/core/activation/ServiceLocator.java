/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.lang.reflect.InvocationHandler;

import kosmos.framework.core.activation.ComponentLocator;
import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.query.AdvancedOrmQueryFactory;
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
	
	/** the ServiceLocator */
	protected static ServiceLocator delegate;
	
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
	 * Look up service using interface.
	 * @param <T> the type
	 * @param ifType the interface of target service
	 * @return the service
	 */
	public static <T> T lookupByInterface(Class<T> ifType){
		return delegate.lookupComponentByInterface(ifType);
	}
	
	/**
	 * Look up service using name.
	 * @param <T> the type
	 * @param name the name of target service
	 * @return the service
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name){
		return (T)delegate.lookupComponent(name);
	}
	
	
	/**
	 * Look up remote service using interface.
	 * @param <T> the type
	 * @param clazz the interface of target service
	 * @return the service
	 */
	public static <T> T lookupRemote(Class<T> clazz){
		return delegate.lookupRemoteComponent(clazz);
	}
	
	/**
	 * @return the MessageBuilder
	 */
	public static MessageBuilder createDefaultMessageBuilder(){
		return delegate.createMessageBuilder();
	}
	
	/**
	 * @return the MessageClientFactory
	 */
	public static MessageClientFactory createDefaultMessageClientFactory(){
		return delegate.createMessageClientFactory();
	}
	
	/**
	 * @return the ServiceActivator
	 */
	public static ServiceActivator createDefaultServiceActivator(){
		return delegate.createServiceActivator();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultPublisher(){
		return delegate.createPublisher();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultSender(){
		return delegate.createSender();
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultQueryFactory(){
		return delegate.createQueryFactory();
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultClientQueryFactory(){
		return delegate.createClientQueryFactory();
	}
	
	/**
	 * @return the AdvancedOrmQueryFactory
	 */
	public static AdvancedOrmQueryFactory createDefaultOrmQueryFactory(){
		return delegate.createOrmQueryFactory();
	}
	
	/**
	 * @return the AsyncServiceFactory
	 */
	public static AsyncServiceFactory createDefaultAsyncServiceFactory(){
		return delegate.createAsyncServiceFactory();
	}
	
	/**
	 * @return the ServiceContext
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ServiceContext> T createDefaultServiceContext(){
		return (T)delegate.createServiceContext();
	}		
	
}
