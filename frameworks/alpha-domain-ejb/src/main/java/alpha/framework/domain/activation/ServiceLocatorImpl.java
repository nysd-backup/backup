/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import alpha.framework.core.message.FaultLogger;
import alpha.framework.core.message.MessageBuilder;
import alpha.framework.core.message.impl.FaultLoggerImpl;
import alpha.framework.core.message.impl.MessageBuilderImpl;
import alpha.framework.domain.advice.InternalPerfInterceptor;
import alpha.framework.domain.advice.InternalQueryBuilderInterceptor;
import alpha.framework.domain.advice.ProxyFactory;
import alpha.framework.domain.exception.BusinessException;
import alpha.framework.domain.messaging.client.MessageClientFactory;
import alpha.framework.domain.messaging.client.MessageProducerImpl;
import alpha.framework.domain.messaging.client.impl.MessageClientFactoryImpl;
import alpha.jdbc.strategy.QueryBuilder;
import alpha.jdbc.strategy.impl.QueryBuilderProxyImpl;
import alpha.sqlclient.elink.free.strategy.InternalNamedQueryImpl;
import alpha.sqlclient.elink.free.strategy.InternalNativeQueryImpl;
import alpha.sqlclient.elink.orm.strategy.JPQLStatementBuilderImpl;
import alpha.sqlclient.free.QueryFactory;
import alpha.sqlclient.free.strategy.InternalQuery;
import alpha.sqlclient.orm.CriteriaQueryFactory;
import alpha.sqlclient.orm.strategy.impl.InternalOrmQueryImpl;


/**
 * A alpha.domain locator.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceLocatorImpl extends ServiceLocator{
	
	/** the JNDI prefix */
	private static final String PREFIX = "java:module";
	
	/**
	 * @param delegatingLocator the delegatingLocator
	 */
	public void setDelegate(ServiceLocatorImpl delegatingLocator){
		delegate = delegatingLocator;
	}
	
	/**
	 * @see alpha.framework.domain.activation.ServiceLocator#lookup(java.lang.String)
	 */
	@Override
	public Object lookup(String serviceName) {
		return lookup(serviceName,null);
	}

	/**
	 * @see alpha.framework.domain.activation.ServiceLocator#lookup(java.lang.Class)
	 */
	@Override
	public Object lookup(Class<?> ifType) {
		return ifType.cast(lookup(ifType.getSimpleName() + "Impl"));
	}

	/**
	 * @see alpha.framework.domain.activation.ServiceLocator#createMessageBuilder()
	 */
	@Override
	public MessageBuilder createMessageBuilder() {
		return new MessageBuilderImpl();
	}
	
	/**
	 * @see alpha.framework.core.activation.ComponentLocator#createBusinessException()
	 */
	@Override
	public alpha.framework.core.exception.BusinessException createBusinessException() {
		return new BusinessException();
	}

	/**
	 * @see alpha.framework.core.activation.ComponentLocator#createFaultNotifier()
	 */
	@Override
	public FaultLogger createFaultNotifier() {
		return new FaultLoggerImpl();
	}
	
	/**
	 * Creates the query factory.
	 * @param em the em to set
	 * @return query factory
	 */
	public QueryFactory createQueryFactory(EntityManager em) {

		//インターセプターを設定する
		InternalNamedQueryImpl named = new InternalNamedQueryImpl();
		InternalNativeQueryImpl ntv = new InternalNativeQueryImpl();
		
		QueryBuilder builder = ProxyFactory.create(QueryBuilder.class, new QueryBuilderProxyImpl(), new InternalQueryBuilderInterceptor(),"evaluate");		
		named.setQueryBuilder(builder);				
		ntv.setQueryBuilder(builder);
		
		InternalQuery namedQuery =  ProxyFactory.create(InternalQuery.class, named, new InternalPerfInterceptor(),"*");		
		InternalQuery nativeQuery =  ProxyFactory.create(InternalQuery.class, ntv, new InternalPerfInterceptor(),"*");		
					
		QueryFactory nf = new QueryFactory();
		nf.setInternalNativeQuery(nativeQuery);
		nf.setInternalNamedQuery(namedQuery);
		
		return nf;	
	}
	
	/**
	 * Creates the query factory.
	 * @param em the em to set
	 * @return query factory
	 */
	public CriteriaQueryFactory createCriteriaQueryFactory(EntityManager em) {

		CriteriaQueryFactory impl = new CriteriaQueryFactory();
		
		InternalOrmQueryImpl dao = new InternalOrmQueryImpl();		
		InternalNamedQueryImpl named = new InternalNamedQueryImpl();	
		//インターセプターを設定する
		QueryBuilder builder = ProxyFactory.create(QueryBuilder.class, new QueryBuilderProxyImpl(), new InternalQueryBuilderInterceptor(),"evaluate");		
		named.setQueryBuilder(builder);
		dao.setInternalQuery(named);		
		dao.setSqlStatementBuilder(new JPQLStatementBuilderImpl());
		impl.setInternalOrmQuery(dao);
		return impl;
	}

	/**
	 * @return the OrmQueryWrapperFactory
	 */
	public static CriteriaQueryFactory createDefaultCriteriaQueryFactory(EntityManager em){
		return ((ServiceLocatorImpl)delegate).createCriteriaQueryFactory(em);
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultQueryFactory(EntityManager em){
		return ((ServiceLocatorImpl)delegate).createQueryFactory(em);
	}
	
	/**
	 * @see alpha.framework.domain.activation.ServiceLocator#createMessageClientFactory()
	 */
	@Override
	public MessageClientFactory createMessageClientFactory() {
		MessageClientFactoryImpl factory = new MessageClientFactoryImpl();
		MessageProducerImpl producer =  new MessageProducerImpl();		
		factory.setQueueProducer(producer);
		factory.setTopicProducer(producer);
		return factory;
	}
	
	/**
	 * @param serviceName the name of alpha.domain
	 * @param prop the properties to look up
	 * @return the alpha.domain
	 */
	private Object lookup(String serviceName, Properties prop){
		InitialContext context = null;
		try{			
			String format = String.format("%s/%s",PREFIX , serviceName);		
			if(prop == null){				
				context = new InitialContext(); 				
			}else{
				context = new InitialContext(prop); 		
			}
			return context.lookup(format);
		}catch(NamingException ne){
			throw new IllegalArgumentException("Failed to load alpha.domain ", ne);
		}finally{
			if(context != null){
				try {
					context.close();
				} catch (NamingException e) {					
					e.printStackTrace();
				}
			}
		}
	}
}

