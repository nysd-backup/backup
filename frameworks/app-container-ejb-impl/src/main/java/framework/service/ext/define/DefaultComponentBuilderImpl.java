/**
 * Use is subject to license terms.
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
 * フレームワークのコンポーネントを生成する.
 * 
 * <pre>
 * SessionBeanはロールバックフラグがたっていると新規作成できないため、設計によっては処理続行不可能となる。(EntityManager単体の取得は可能）
 * フレームワークの各種コンポーネントをSessionBeanとしてしまうと逃げがきかなくなるためPOJOとして生成する。
 * (ただしくはSessionBeanのメソッドコール時にインターセプターでTransactionRolledBackExceptionがスローされる）
 * ただし、EntityManagerの提供者のみ、AsyncServiceはSessionBeanとせざるを得ないのでSessionBeanとする。
 *
 * インターセプターを仕込みたければPOJOでなく、独自で動的プロキシを使うかCDIを使用すること。
 * </pre>
 * 
 * @author yoshida-n
 * @version	created.
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
		AsyncServiceFactoryImpl impl = new AsyncServiceFactoryImpl();
		return impl;
	}
	
	/**
	 * @return エンティティマネージャの供給者
	 */
	protected EntityManagerProvider createEntityManagerProvider() {
		return ServiceLocator.lookupByInterface(EntityManagerProvider.class);
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createNativeQueryFactory()
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
