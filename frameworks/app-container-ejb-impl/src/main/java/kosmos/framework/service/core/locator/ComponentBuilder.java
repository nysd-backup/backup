/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import java.lang.reflect.InvocationHandler;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.service.ServiceActivator;
import kosmos.framework.logics.builder.MessageAccessor;
import kosmos.framework.service.core.async.AsyncServiceFactory;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.sqlclient.api.free.QueryFactory;


/**
 * A Component builder instead of DI container.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ComponentBuilder {
	
	/**
	 * @return the <code>MessageClientFactory</code>
	 */
	public MessageClientFactory createMessagingClientFactory();
	
	/**
	 * @return the <code>ServiceActivatorImpl</code>
	 */
	public ServiceActivator createDelegatingServiceInvoker();
	
	/**
	 * @return the JMS publisher
	 */
	public InvocationHandler createPublisher();
	
	/**
	 * @return the JMS sender
	 */
	public InvocationHandler createSender();
	
	/**
	 * @return the <code>MessageAccessor</code>
	 */
	public MessageAccessor createMessageAccessor();
	
	/**
	 * @return the <code>QueryFactory</code>
	 */
	public QueryFactory createQueryFactory();
	
	/**
	 * @return the <code>QueryFactory</code> only called from WEB 
	 */
	public QueryFactory createNativeQueryFactory();
	
	/**
	 * @return the <code>AsyncServiceFactory</code>
	 */
	public AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return the <code>AdvancedOrmQueryFactory</code>
	 */
	public AdvancedOrmQueryFactory createOrmQueryFactory();
	
}
