/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.lang.reflect.InvocationHandler;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.logics.log.FaultNotifier;
import kosmos.framework.core.logics.log.impl.DefaultFaultNotifier;
import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.logics.message.impl.MessageBuilderImpl;
import kosmos.framework.core.message.ExceptionMessageFactory;
import kosmos.framework.core.message.impl.DefaultExceptionMessageFactoryImpl;
import kosmos.framework.service.core.async.AsyncServiceFactory;
import kosmos.framework.service.core.async.AsyncServiceFactoryImpl;
import kosmos.framework.service.core.exception.ApplicationException;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.core.messaging.MessageClientFactoryImpl;
import kosmos.framework.service.core.messaging.QueueProducerDelegate;
import kosmos.framework.service.core.messaging.TopicProducerDelegate;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.ServiceContextImpl;


/**
 * A service locator.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractServiceLocator extends ServiceLocator{

	/** the JNDI prefix */
	private static final String PREFIX = "java:module";
	
	/** the remoting properties */
	private final Properties remotingProperties;
	
	/**
	 * @param componentBuilder the componentBuilder to set
	 * @param remotingProperties the remotingProperties to set
	 */
	public AbstractServiceLocator(Properties remotingProperties){
		this.remotingProperties = remotingProperties;
		delegate = this;
	}
	
	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#lookupComponentByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupComponentByInterface(Class<T> clazz) {
		return clazz.cast(lookup(clazz.getSimpleName() + "Impl",null));
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#lookupComponent(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupComponent(String name) {
		return (T)lookup(name,null);
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#lookupRemoteComponent(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteComponent(Class<T> serviceType) {
	     Object service = lookup(serviceType.getSimpleName() , remotingProperties);
	     return serviceType.cast(service);
	}
	
	/**
	 * @param serviceName the name of service
	 * @param prop the properties to look up
	 * @return the service
	 */
	protected Object lookup(String serviceName, Properties prop){
		
		try{			
			String format = String.format("%s/%s",PREFIX , serviceName);		
			if(prop == null){				
				return new InitialContext().lookup(format);
			}else{
				return new InitialContext(prop).lookup(format);
			}
		}catch(NamingException ne){
			throw new IllegalArgumentException("Failed to load service ", ne);
		}
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createServiceContext()
	 */
	@Override
	public ServiceContext createServiceContext() {
		return new ServiceContextImpl();
	}
	
	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createAsyncServiceFactory()
	 */
	@Override
	public AsyncServiceFactory createAsyncServiceFactory() {
		return new AsyncServiceFactoryImpl();
	}

	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		return new TopicProducerDelegate();
	}

	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createSender()
	 */
	@Override
	public InvocationHandler createSender() {
		return new QueueProducerDelegate();
	}

	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createMessagingClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		return new MessageClientFactoryImpl();
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createServiceActivator()
	 */
	@Override
	public ServiceActivator createServiceActivator() {
		return new ServiceActivatorImpl();
	}

	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createMessageBuilder()
	 */
	@Override
	public MessageBuilder createMessageBuilder() {
		return new MessageBuilderImpl();
	}
	
	/**
	 * @see kosmos.framework.core.activation.ComponentLocator#createBusinessException()
	 */
	@Override
	public BusinessException createBusinessException() {
		return new ApplicationException();
	}

	/**
	 * @see kosmos.framework.core.activation.ComponentLocator#createFaultNotifier()
	 */
	@Override
	public FaultNotifier createFaultNotifier() {
		return new DefaultFaultNotifier();
	}

	/**
	 * @see kosmos.framework.core.activation.ComponentLocator#createExceptionMessageFactory()
	 */
	@Override
	public ExceptionMessageFactory createExceptionMessageFactory() {
		return new DefaultExceptionMessageFactoryImpl();
	}

}

