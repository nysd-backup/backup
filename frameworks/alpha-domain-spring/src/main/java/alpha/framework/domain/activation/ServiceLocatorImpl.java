/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import org.springframework.context.ApplicationContext;

import alpha.framework.core.exception.BusinessException;
import alpha.framework.core.message.FaultLogger;
import alpha.framework.core.message.MessageBuilder;
import alpha.framework.domain.activation.ServiceLocator;
import alpha.framework.domain.messaging.client.MessageClientFactory;

import client.sql.free.QueryFactory;


/**
 * The alpha.domain locator using Spring
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
	 * @see alpha.framework.domain.activation.ServiceLocator#lookupComponentByInterface(java.lang.Class)
	 */
	@Override
	public Object lookup(Class<?> clazz) {
		return context.getBean(clazz);
	}

	/**
	 * @see alpha.framework.domain.activation.ServiceLocator#lookupComponent(java.lang.String)
	 */
	@Override
	public Object lookup(String serviceName) {
		return context.getBean(serviceName);
	}
	
	/**
	 * @see alpha.framework.domain.activation.ServiceLocator#getMessageBuilder()
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
	 * @see alpha.framework.core.activation.ComponentLocator#createBusinessException()
	 */
	@Override
	public BusinessException createBusinessException(){
		return new BusinessException();
	}
	
	/**
	 * @see alpha.framework.core.activation.ComponentLocator#createFaultNotifier()
	 */
	@Override
	public FaultLogger createFaultNotifier(){
		return FaultLogger.class.cast(lookup(FaultLogger.class));
	}

	/**
	 * @see alpha.framework.domain.activation.ServiceLocator#createMessageClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		return MessageClientFactory.class.cast(lookup(MessageClientFactory.class));
	}
	

}
