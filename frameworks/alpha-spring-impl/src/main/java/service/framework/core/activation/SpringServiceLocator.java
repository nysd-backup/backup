/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;

import java.lang.reflect.InvocationHandler;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import client.sql.free.QueryFactory;
import client.sql.orm.OrmQueryFactory;

import core.exception.BusinessException;
import core.logics.log.FaultNotifier;
import core.message.ExceptionMessageFactory;
import core.message.MessageBuilder;

import service.client.messaging.MessageClientFactory;
import service.framework.core.activation.ServiceLocator;
import service.framework.core.async.AsyncServiceFactory;
import service.framework.core.transaction.ServiceContext;


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
	 * @see service.framework.core.activation.ServiceLocator#lookupComponentByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupComponentByInterface(Class<T> clazz) {
		return clazz.cast(context.getBean(clazz));
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#lookupComponent(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupComponent(String name) {
		return (T)context.getBean(name);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#lookupRemoteComponent(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteComponent(Class<T> serviceType){		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @see service.framework.core.activation.ServiceLocator#createServiceContext()
	 */
	@Override
	public ServiceContext createServiceContext() {
		return new ServiceContext();
	}
	
	/**
	 * @see service.framework.core.activation.ServiceLocator#getMessageBuilder()
	 */
	@Override
	public MessageBuilder createMessageBuilder(){
		return lookupByInterface(MessageBuilder.class);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createMessageClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		return lookupByInterface(MessageClientFactory.class);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		return lookup("topicProducer");
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createSender()
	 */
	@Override
	public InvocationHandler createSender() {
		return lookup("queueProducer");
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		return lookupByInterface(QueryFactory.class);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createAsyncServiceFactory()
	 */
	@Override
	public AsyncServiceFactory createAsyncServiceFactory() {
		return lookupByInterface(AsyncServiceFactory.class);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createOrmQueryFactory()
	 */
	@Override
	public OrmQueryFactory createOrmQueryFactory() {
		return lookupByInterface(OrmQueryFactory.class);
	}
	
	/**
	 * @see core.activation.ComponentLocator#createBusinessException()
	 */
	@Override
	public BusinessException createBusinessException(){
		return new BusinessException();
	}
	
	/**
	 * @see core.activation.ComponentLocator#createFaultNotifier()
	 */
	@Override
	public FaultNotifier createFaultNotifier(){
		return lookupByInterface(FaultNotifier.class);
	}


	/**
	 * @see core.activation.ComponentLocator#createExceptionMessageFactory()
	 */
	@Override
	public ExceptionMessageFactory createExceptionMessageFactory() {
		return lookupByInterface(ExceptionMessageFactory.class);
	}

}
