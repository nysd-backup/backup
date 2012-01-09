/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.lang.reflect.InvocationHandler;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.logics.log.FaultNotifier;
import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.query.LimitedOrmQueryFactory;
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
	 * @see kosmos.framework.service.core.activation.ServiceLocator#lookupComponentByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupComponentByInterface(Class<T> clazz) {
		return clazz.cast(context.getBean(clazz));
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#lookupComponent(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupComponent(String name) {
		return (T)context.getBean(name);
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#lookupRemoteComponent(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteComponent(Class<T> serviceType){		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createServiceContext()
	 */
	@Override
	public ServiceContext createServiceContext() {
		return new ServiceContextImpl();
	}
	
	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#getMessageBuilder()
	 */
	@Override
	public MessageBuilder createMessageBuilder(){
		return lookupByInterface(MessageBuilder.class);
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createMessageClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		return lookupByInterface(MessageClientFactory.class);
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createServiceActivator()
	 */
	@Override
	public ServiceActivator createServiceActivator() {
		return new ServiceActivatorImpl();
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		return lookup("topicProducer");
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createSender()
	 */
	@Override
	public InvocationHandler createSender() {
		return lookup("queueProducer");
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		return lookupByInterface(QueryFactory.class);
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createAsyncServiceFactory()
	 */
	@Override
	public AsyncServiceFactory createAsyncServiceFactory() {
		return lookupByInterface(AsyncServiceFactory.class);
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createOrmQueryFactory()
	 */
	@Override
	public LimitedOrmQueryFactory createOrmQueryFactory() {
		return lookupByInterface(LimitedOrmQueryFactory.class);
	}
	
	/**
	 * @see kosmos.framework.core.activation.ComponentLocator#createBusinessException()
	 */
	@Override
	public BusinessException createBusinessException(){
		return new BusinessException();
	}
	
	/**
	 * @see kosmos.framework.core.activation.ComponentLocator#createFaultNotifier()
	 */
	@Override
	public FaultNotifier createFaultNotifier(){
		return lookupByInterface(FaultNotifier.class);
	}
	
}
