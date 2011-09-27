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
import framework.service.core.persistence.EntityManagerAccessor;
import framework.service.core.persistence.EntityManagerAccessorImpl;
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
 * 繝輔Ξ繝ｼ繝繝ｯ繝ｼ繧ｯ縺ｮ繧ｳ繝ｳ繝昴・繝阪Φ繝医ｒ逕滓・縺吶ｋ.
 * 
 * <pre>
 * SessionBean縺ｯ繝ｭ繝ｼ繝ｫ繝舌ャ繧ｯ繝輔Λ繧ｰ縺後◆縺｣縺ｦ縺・ｋ縺ｨ譁ｰ隕丈ｽ懈・縺ｧ縺阪↑縺・◆繧√∬ｨｭ險医↓繧医▲縺ｦ縺ｯ蜃ｦ逅・ｶ夊｡御ｸ榊庄閭ｽ縺ｨ縺ｪ繧九・EntityManager蜊倅ｽ薙・蜿門ｾ励・蜿ｯ閭ｽ・・
 * 繝輔Ξ繝ｼ繝繝ｯ繝ｼ繧ｯ縺ｮ蜷・ｨｮ繧ｳ繝ｳ繝昴・繝阪Φ繝医ｒSessionBean縺ｨ縺励※縺励∪縺・→騾・￡縺後″縺九↑縺上↑繧九◆繧￣OJO縺ｨ縺励※逕滓・縺吶ｋ縲・
 * (縺溘□縺励￥縺ｯSessionBean縺ｮ繝｡繧ｽ繝・ラ繧ｳ繝ｼ繝ｫ譎ゅ↓繧､繝ｳ繧ｿ繝ｼ繧ｻ繝励ち繝ｼ縺ｧTransactionRolledBackException縺後せ繝ｭ繝ｼ縺輔ｌ繧具ｼ・
 * 縺溘□縺励・ntityManager縺ｮ謠蝉ｾ幄・・縺ｿ縲、syncService縺ｯSessionBean縺ｨ縺帙＊繧九ｒ蠕励↑縺・・縺ｧSessionBean縺ｨ縺吶ｋ縲・
 *
 * 繧､繝ｳ繧ｿ繝ｼ繧ｻ繝励ち繝ｼ繧剃ｻ戊ｾｼ縺ｿ縺溘￠繧後・POJO縺ｧ縺ｪ縺上∫峡閾ｪ縺ｧ蜍慕噪繝励Ο繧ｭ繧ｷ繧剃ｽｿ縺・°CDI繧剃ｽｿ逕ｨ縺吶ｋ縺薙→縲・
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
	 * @see framework.service.ext.define.ComponentBuilder#createEntityManagerAccessor()
	 */
	@Override
	public EntityManagerAccessor createEntityManagerAccessor() {
		EntityManagerAccessorImpl accessor = new EntityManagerAccessorImpl();
		accessor.setEntityManagerProvider(createEntityManagerProvider());
		return accessor;
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
	 * @return 繧ｨ繝ｳ繝・ぅ繝・ぅ繝槭ロ繝ｼ繧ｸ繝｣縺ｮ萓帷ｵｦ閠・
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
