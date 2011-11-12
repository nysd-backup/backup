/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import java.lang.reflect.InvocationHandler;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.service.ServiceActivator;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.jpqlclient.api.free.EclipseLinkQueryFactoryImpl;
import kosmos.framework.jpqlclient.api.orm.OrmQueryFactoryImpl;
import kosmos.framework.jpqlclient.internal.orm.impl.GenericJPADaoImpl;
import kosmos.framework.logics.builder.MessageAccessor;
import kosmos.framework.logics.builder.impl.MessageBuilderImpl;
import kosmos.framework.service.core.async.AsyncServiceFactory;
import kosmos.framework.service.core.async.AsyncServiceFactoryImpl;
import kosmos.framework.service.core.error.MessageAccessorImpl;
import kosmos.framework.service.core.locator.ComponentBuilder;
import kosmos.framework.service.core.locator.ServiceActivatorImpl;
import kosmos.framework.service.core.locator.ServiceLocator;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.core.messaging.MessageClientFactoryImpl;
import kosmos.framework.service.core.messaging.QueueProducerDelegate;
import kosmos.framework.service.core.messaging.TopicProducerDelegate;
import kosmos.framework.service.core.query.AdvancedOrmQueryFactoryImpl;
import kosmos.framework.service.core.query.CustomEmptyHandlerImpl;
import kosmos.framework.service.core.query.CustomMultiResultHandlerImpl;
import kosmos.framework.sqlclient.api.free.QueryFactory;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultComponentBuilder implements ComponentBuilder {

	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createMessageAccessor()
	 */
	@Override
	public MessageAccessor createMessageAccessor() {
		MessageAccessorImpl accessor = new MessageAccessorImpl();
		accessor.setMessageBuilder(new MessageBuilderImpl());
		return accessor;
	}

	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		EclipseLinkQueryFactoryImpl factory = new EclipseLinkQueryFactoryImpl();
		factory.setEmptyHandler( new CustomEmptyHandlerImpl());
		factory.setEntityManagerProvider(createEntityManagerProvider());		
		return factory;	
	}

	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createOrmQueryFactory()
	 */
	@Override
	public AdvancedOrmQueryFactory createOrmQueryFactory() {
		AdvancedOrmQueryFactoryImpl impl = new AdvancedOrmQueryFactoryImpl();
		OrmQueryFactoryImpl internal = new OrmQueryFactoryImpl();
		GenericJPADaoImpl dao = new GenericJPADaoImpl();
		dao.setEntityManagerProvider(createEntityManagerProvider());	
		dao.setEmptyHandler(new CustomEmptyHandlerImpl());
		dao.setMultiResultHandler(new CustomMultiResultHandlerImpl());
		internal.setGenericDao(dao);
		impl.setInternalFactory(internal);
		return impl;
	}
	
	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createAsyncServiceFactory()
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
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createNativeQueryFactory()
	 */
	@Override
	public QueryFactory createNativeQueryFactory() {
		return createQueryFactory();
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
	public MessageClientFactory createMessagingClientFactory() {
		return new MessageClientFactoryImpl();
	}

	@Override
	public ServiceActivator createDelegatingServiceInvoker() {
		return new ServiceActivatorImpl();
	}

}
