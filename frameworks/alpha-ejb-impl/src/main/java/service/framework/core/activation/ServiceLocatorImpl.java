/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;

import java.lang.reflect.InvocationHandler;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import client.sql.elink.EntityManagerProvider;
import client.sql.elink.free.strategy.InternalNamedQueryImpl;
import client.sql.elink.free.strategy.InternalNativeQueryImpl;
import client.sql.elink.orm.strategy.InternalOrmQueryImpl;
import client.sql.free.QueryFactory;
import client.sql.free.strategy.InternalQuery;
import client.sql.orm.OrmQueryFactory;

import core.exception.BusinessException;
import core.logics.log.FaultNotifier;
import core.logics.log.impl.DefaultFaultNotifier;
import core.message.ExceptionMessageFactory;
import core.message.MessageBuilder;
import core.message.impl.DefaultExceptionMessageFactoryImpl;
import core.message.impl.MessageBuilderImpl;


import service.client.messaging.MessageClientFactory;
import service.client.messaging.MessageClientFactoryImpl;
import service.client.messaging.QueueProducerDelegate;
import service.client.messaging.TopicProducerDelegate;
import service.framework.core.activation.ServiceLocator;
import service.framework.core.advice.InternalPerfInterceptor;
import service.framework.core.advice.InternalSQLBuilderInterceptor;
import service.framework.core.advice.ProxyFactory;
import service.framework.core.async.AsyncServiceFactory;
import service.framework.core.async.AsyncServiceFactoryImpl;
import service.framework.core.exception.ServiceException;
import service.framework.core.transaction.ServiceContext;
import service.framework.core.transaction.ServiceContextImpl;
import sqlengine.builder.SQLBuilder;
import sqlengine.builder.impl.SQLBuilderProxyImpl;



/**
 * A service locator.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceLocatorImpl extends ServiceLocator{

	/** the JNDI prefix */
	private static final String PREFIX = "java:module";
	
	/** the remoting properties */
	private final Properties remotingProperties;
	
	/**
	 * @param componentBuilder the componentBuilder to set
	 * @param remotingProperties the remotingProperties to set
	 */
	public ServiceLocatorImpl(Properties remotingProperties){
		this.remotingProperties = remotingProperties;
		delegate = this;
	}
	
	/**
	 * @see service.framework.core.activation.ServiceLocator#lookupComponentByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupComponentByInterface(Class<T> clazz) {
		return clazz.cast(lookup(clazz.getSimpleName() + "Impl",null));
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#lookupComponent(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupComponent(String name) {
		return (T)lookup(name,null);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#lookupRemoteComponent(java.lang.Class)
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
	 * @see service.framework.core.activation.ServiceLocator#createServiceContext()
	 */
	@Override
	public ServiceContext createServiceContext() {
		return new ServiceContextImpl();
	}
	
	/**
	 * @see service.framework.core.define.ComponentBuilder#createAsyncServiceFactory()
	 */
	@Override
	public AsyncServiceFactory createAsyncServiceFactory() {
		return new AsyncServiceFactoryImpl();
	}

	/**
	 * @see service.framework.core.define.ComponentBuilder#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		return new TopicProducerDelegate();
	}

	/**
	 * @see service.framework.core.define.ComponentBuilder#createSender()
	 */
	@Override
	public InvocationHandler createSender() {
		return new QueueProducerDelegate();
	}

	/**
	 * @see service.framework.core.define.ComponentBuilder#createMessagingClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		return new MessageClientFactoryImpl();
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createMessageBuilder()
	 */
	@Override
	public MessageBuilder createMessageBuilder() {
		return new MessageBuilderImpl();
	}
	
	/**
	 * @see core.activation.ComponentLocator#createBusinessException()
	 */
	@Override
	public BusinessException createBusinessException() {
		return new ServiceException();
	}

	/**
	 * @see core.activation.ComponentLocator#createFaultNotifier()
	 */
	@Override
	public FaultNotifier createFaultNotifier() {
		return new DefaultFaultNotifier();
	}

	/**
	 * @see core.activation.ComponentLocator#createExceptionMessageFactory()
	 */
	@Override
	public ExceptionMessageFactory createExceptionMessageFactory() {
		return new DefaultExceptionMessageFactoryImpl();
	}
	
	
	/**
	 * @see service.framework.core.define.ComponentBuilder#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {

		//インターセプターを設定する
		InternalNamedQueryImpl named = new InternalNamedQueryImpl();
		InternalNativeQueryImpl ntv = new InternalNativeQueryImpl();
		
		SQLBuilder builder = ProxyFactory.create(SQLBuilder.class, new SQLBuilderProxyImpl(), new InternalSQLBuilderInterceptor(),"evaluate");		
		named.setSqlBuilder(builder);
		named.setEntityManagerProvider(createEntityManagerProvider());
		
		ntv.setEntityManagerProvider(createEntityManagerProvider());				
		ntv.setSqlBuilder(builder);
		
		InternalQuery namedQuery =  ProxyFactory.create(InternalQuery.class, named, new InternalPerfInterceptor(),"*");		
		InternalQuery nativeQuery =  ProxyFactory.create(InternalQuery.class, ntv, new InternalPerfInterceptor(),"*");		
					
		QueryFactory nf = new QueryFactory();
		nf.setInternalNativeQuery(nativeQuery);
		nf.setInternalNamedQuery(namedQuery);
		
		return nf;	
	}
	
	/**
	 * @see service.framework.core.define.ComponentBuilder#createOrmQueryFactory()
	 */
	@Override
	public OrmQueryFactory createOrmQueryFactory() {
		
		OrmQueryFactory impl = new OrmQueryFactory();
		
		InternalOrmQueryImpl dao = new InternalOrmQueryImpl();
		EntityManagerProvider provider = createEntityManagerProvider();
		InternalNamedQueryImpl named = new InternalNamedQueryImpl();
		named.setEntityManagerProvider(provider);
		
		//インターセプターを設定する
		SQLBuilder builder = ProxyFactory.create(SQLBuilder.class, new SQLBuilderProxyImpl(), new InternalSQLBuilderInterceptor(),"evaluate");		
		named.setSqlBuilder(builder);
		dao.setInternalNamedQuery(named);
		dao.setEntityManagerProvider(provider);	
		impl.setInternalOrmQuery(dao);
		return impl;
	}
	
	/**
	 * @return EntityManagerProvider
	 */
	protected EntityManagerProvider createEntityManagerProvider() {
		return lookupByInterface(EntityManagerProvider.class);
	}
	

}

