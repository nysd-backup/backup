/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.lang.reflect.InvocationHandler;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.logics.log.FaultNotifier;
import kosmos.framework.core.logics.log.impl.DefaultFaultNotifier;
import kosmos.framework.core.message.ExceptionMessageFactory;
import kosmos.framework.core.message.MessageBuilder;
import kosmos.framework.core.message.impl.DefaultExceptionMessageFactoryImpl;
import kosmos.framework.core.message.impl.MessageBuilderImpl;
import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.jpqlclient.free.strategy.InternalNamedQueryImpl;
import kosmos.framework.jpqlclient.free.strategy.InternalNativeQueryImpl;
import kosmos.framework.jpqlclient.orm.strategy.InternalOrmQueryImpl;
import kosmos.framework.service.core.advice.InternalPerfInterceptor;
import kosmos.framework.service.core.advice.InternalSQLBuilderInterceptor;
import kosmos.framework.service.core.advice.ProxyFactory;
import kosmos.framework.service.core.async.AsyncServiceFactory;
import kosmos.framework.service.core.async.AsyncServiceFactoryImpl;
import kosmos.framework.service.core.exception.ApplicationException;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.core.messaging.MessageClientFactoryImpl;
import kosmos.framework.service.core.messaging.QueueProducerDelegate;
import kosmos.framework.service.core.messaging.TopicProducerDelegate;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.service.core.transaction.ServiceContextImpl;
import kosmos.framework.sqlclient.free.QueryFactory;
import kosmos.framework.sqlclient.free.strategy.InternalQuery;
import kosmos.framework.sqlclient.orm.OrmQueryFactory;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.builder.impl.SQLBuilderProxyImpl;


/**
 * A service locator.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceLocatorImpl extends ServiceLocator{

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
	
	
	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createQueryFactory()
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
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createOrmQueryFactory()
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

