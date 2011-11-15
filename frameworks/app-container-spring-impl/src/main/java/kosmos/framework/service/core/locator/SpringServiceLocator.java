/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import java.lang.reflect.InvocationHandler;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.service.ServiceActivator;
import kosmos.framework.api.service.ServiceActivatorImpl;
import kosmos.framework.logics.builder.MessageBuilder;
import kosmos.framework.service.core.async.AsyncServiceFactory;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.ServiceContextImpl;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * The service locator using Spring
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class SpringServiceLocator extends ServiceLocator{
	
	/** the context */
	protected ApplicationContext context = null;	

	/**
	 * Initialize the context.
	 */
	public abstract void construct();
	
	/**
	 * Terminate the context.
	 */
	public abstract void destroy();
	
	/**
	 * Initialize the context.
	 * @param classpathResource the resource
	 */
	protected void initialize(String classpathResource) {
		context = new ClassPathXmlApplicationContext(classpathResource);
		delegate = this;
	}
	
	/**
	 * Terminate the context.
	 */
	protected void terminate(){		
		if(context != null){
			((ClassPathXmlApplicationContext)context).destroy();
			context = null;
		}
		delegate = null;
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(context.getBean(clazz));
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)context.getBean(name);
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#lookupRemoteService(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteService(Class<T> serviceType){		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createContext()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ServiceContext createContext() {
		return new ServiceContextImpl();
	}
	
	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#getMessageBuilder()
	 */
	@Override
	public MessageBuilder createMessageBuilder(){
		return lookupByInterface(MessageBuilder.class);
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createMessageClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		return lookupByInterface(MessageClientFactory.class);
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createServiceActivator()
	 */
	@Override
	public ServiceActivator createServiceActivator() {
		return new ServiceActivatorImpl();
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		return lookup("topicProducer");
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createSender()
	 */
	@Override
	public InvocationHandler createSender() {
		return lookup("queueProducer");
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		return lookupByInterface(QueryFactory.class);
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createClientQueryFactory()
	 */
	@Override
	public QueryFactory createClientQueryFactory() {
		return lookup("clientQueryFactory");
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createAsyncServiceFactory()
	 */
	@Override
	public AsyncServiceFactory createAsyncServiceFactory() {
		return lookupByInterface(AsyncServiceFactory.class);
	}

	/**
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createOrmQueryFactory()
	 */
	@Override
	public AdvancedOrmQueryFactory createOrmQueryFactory() {
		return lookupByInterface(AdvancedOrmQueryFactory.class);
	}
	
}
