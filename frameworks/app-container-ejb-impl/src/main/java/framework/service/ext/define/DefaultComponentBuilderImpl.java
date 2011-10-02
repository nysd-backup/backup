/**
 * Copyright 2011 the original author
 */
package framework.service.ext.define;

import java.lang.reflect.InvocationHandler;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.service.RequestListener;
import framework.core.message.MessageBean;
import framework.jpqlclient.api.EntityManagerProvider;
import framework.jpqlclient.api.free.JPAQueryFactoryImpl;
import framework.jpqlclient.api.free.NativeQueryFactoryImpl;
import framework.jpqlclient.api.orm.OrmQueryFactoryImpl;
import framework.jpqlclient.internal.orm.impl.GenericJPADaoImpl;
import framework.logics.builder.MessageAccessor;
import framework.logics.builder.impl.MessageBuilderImpl;
import framework.service.core.async.AsyncServiceFactory;
import framework.service.core.error.MessageAccessorImpl;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.messaging.MessageClientFactory;
import framework.service.core.query.AdvancedOrmQueryFactoryImpl;
import framework.service.core.query.CustomEmptyHandlerImpl;
import framework.service.core.query.DataSourceConnectionProviderImpl;
import framework.service.ext.async.AsyncServiceFactoryImpl;
import framework.service.ext.listener.RequestListenerImpl;
import framework.service.ext.messaging.MessageClientFactoryImpl;
import framework.service.ext.messaging.QueueProducerDelegate;
import framework.service.ext.messaging.TopicProducerDelegate;
import framework.sqlclient.api.free.QueryFactory;
import framework.sqlengine.facade.impl.SQLEngineFacadeImpl;

/**
 * A Component builder instead of DI container.
 * 
 * <pre>
 * <code>SessionBean</code> can not be created if the transaction is marked as rolled back.
 * so build the components as POJO except <code>EntityManagerProvider</code>.
 * <code>EntityManagerProvider</code> must be <code>SessionBean</code> to create an <code>EntityManager</code>.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultComponentBuilderImpl implements ComponentBuilder {

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createMessageAccessor()
	 */
	@Override
	public MessageAccessor<MessageBean> createMessageAccessor() {
		MessageAccessorImpl accessor = new MessageAccessorImpl();
		accessor.setMessageBuilder(new MessageBuilderImpl());
		return accessor;
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		JPAQueryFactoryImpl factory = new JPAQueryFactoryImpl();
		factory.setEmptyHandler( new CustomEmptyHandlerImpl());
		factory.setEntityManagerProvider(createEntityManagerProvider());		
		return factory;	
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createOrmQueryFactory()
	 */
	@Override
	public AdvancedOrmQueryFactory createOrmQueryFactory() {
		AdvancedOrmQueryFactoryImpl impl = new AdvancedOrmQueryFactoryImpl();
		OrmQueryFactoryImpl internal = new OrmQueryFactoryImpl();
		
		GenericJPADaoImpl dao = new GenericJPADaoImpl();
		dao.setEntityManagerProvider(createEntityManagerProvider());
	
		internal.setGenericDao(dao);
	
		impl.setInternalFactory(internal);
		return impl;
	}
	
	/**
	 * @see framework.service.ext.define.ComponentBuilder#createAsyncServiceFactory()
	 */
	@Override
	public AsyncServiceFactory createAsyncServiceFactory() {
		return new AsyncServiceFactoryImpl();
	}
	
	/**
	 * @return EntityManagerProvider
	 */
	protected EntityManagerProvider createEntityManagerProvider() {
		return ServiceLocator.lookupByInterface(EntityManagerProvider.class);
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createWebQueryFactory()
	 */
	@Override
	public QueryFactory createWebQueryFactory() {
		NativeQueryFactoryImpl factory = new NativeQueryFactoryImpl();
		factory.setEmptyHandler( new CustomEmptyHandlerImpl());
		factory.setConnectionProvider(new DataSourceConnectionProviderImpl());
		factory.setSqlEngineFacade(new SQLEngineFacadeImpl());
		return factory;	
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		return new TopicProducerDelegate();
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createSender()
	 */
	@Override
	public InvocationHandler createSender() {
		return new QueueProducerDelegate();
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createMessagingClientFactory()
	 */
	@Override
	public MessageClientFactory createMessagingClientFactory() {
		return new MessageClientFactoryImpl();
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createRequestListener()
	 */
	@Override
	public RequestListener createRequestListener() {
		return new RequestListenerImpl();
	}

}
