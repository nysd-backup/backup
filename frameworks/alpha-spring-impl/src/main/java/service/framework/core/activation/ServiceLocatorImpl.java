/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;

import org.springframework.context.ApplicationContext;

import service.client.messaging.MessageClientFactory;
import service.framework.core.async.AsyncService;
import client.sql.free.QueryFactory;
import core.exception.BusinessException;
import core.logics.log.FaultNotifier;
import core.message.MessageBuilder;


/**
 * The service locator using Spring
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceLocatorImpl extends ServiceLocator{
	
	/** the context */
	protected ApplicationContext context = null;
	
	/**
	 * @param context the context
	 */
	public ServiceLocatorImpl(ApplicationContext context){
		this.context = context;
	}
	
	/**
	 * @param delegatingLocator the delegatingLocator to set
	 */
	public static void setDelegate(ServiceLocator delegatingLocator){
		delegate = delegatingLocator;
	}
	
	/**
	 * @see service.framework.core.activation.ServiceLocator#lookupComponentByInterface(java.lang.Class)
	 */
	@Override
	public Object lookup(Class<?> clazz) {
		return context.getBean(clazz);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#lookupComponent(java.lang.String)
	 */
	@Override
	public Object lookup(String serviceName) {
		return context.getBean(serviceName);
	}
	
	/**
	 * @see service.framework.core.activation.ServiceLocator#getMessageBuilder()
	 */
	@Override
	public MessageBuilder createMessageBuilder(){
		return MessageBuilder.class.cast(lookup(MessageBuilder.class));
	}

	/**
	 * @return query factory
	 */
	public QueryFactory createQueryFactory() {
		return QueryFactory.class.cast(lookup(QueryFactory.class));
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
		return FaultNotifier.class.cast(lookup(FaultNotifier.class));
	}
	
	/**
	 * @see service.framework.core.activation.ServiceLocator#createAsyncService()
	 */
	@Override
	public AsyncService createAsyncService() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createMessageClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		return MessageClientFactory.class.cast(lookup(MessageClientFactory.class));
	}
	

}
