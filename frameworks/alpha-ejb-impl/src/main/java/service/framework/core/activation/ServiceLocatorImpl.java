/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;

import java.lang.reflect.InvocationHandler;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import service.client.messaging.MessageProducerImpl;
import service.framework.core.advice.InternalPerfInterceptor;
import service.framework.core.advice.InternalSQLBuilderInterceptor;
import service.framework.core.advice.ProxyFactory;
import service.framework.core.async.AsyncService;
import service.framework.core.async.AsyncServiceImpl;
import service.framework.core.exception.BusinessException;
import sqlengine.builder.QueryBuilder;
import sqlengine.builder.impl.QueryBuilderProxyImpl;
import client.sql.elink.free.strategy.InternalNamedQueryImpl;
import client.sql.elink.free.strategy.InternalNativeQueryImpl;
import client.sql.elink.orm.strategy.JPQLStatementBuilderImpl;
import client.sql.free.QueryFactory;
import client.sql.free.strategy.InternalQuery;
import client.sql.orm.CriteriaQueryFactory;
import client.sql.orm.strategy.InternalOrmQueryImpl;
import core.logics.log.FaultNotifier;
import core.logics.log.impl.DefaultFaultNotifier;
import core.message.MessageBuilder;
import core.message.impl.MessageBuilderImpl;


/**
 * A service locator.
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
	 * @see service.framework.core.activation.ServiceLocator#lookup(java.lang.String)
	 */
	@Override
	public Object lookup(String serviceName) {
		return lookup(serviceName,null);
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#lookup(java.lang.Class)
	 */
	@Override
	public Object lookup(Class<?> ifType) {
		return ifType.cast(lookup(ifType.getSimpleName() + "Impl"));
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		MessageProducerImpl sender = new MessageProducerImpl();
		sender.setConnectionFactoryName("jms/topic/DefaultXAConFactory");
		return sender;
	}

	/**
	 * @see service.framework.core.activation.ServiceLocator#createSender()
	 */
	@Override
	public InvocationHandler createSender() {		
		MessageProducerImpl sender = new MessageProducerImpl();
		sender.setConnectionFactoryName("jms/queue/DefaultXAConFactory");
		return sender;
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
	public core.exception.BusinessException createBusinessException() {
		return new BusinessException();
	}

	/**
	 * @see core.activation.ComponentLocator#createFaultNotifier()
	 */
	@Override
	public FaultNotifier createFaultNotifier() {
		return new DefaultFaultNotifier();
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
		
		QueryBuilder builder = ProxyFactory.create(QueryBuilder.class, new QueryBuilderProxyImpl(), new InternalSQLBuilderInterceptor(),"evaluate");		
		named.setSqlBuilder(builder);
				
		ntv.setSqlBuilder(builder);
		
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
		QueryBuilder builder = ProxyFactory.create(QueryBuilder.class, new QueryBuilderProxyImpl(), new InternalSQLBuilderInterceptor(),"evaluate");		
		named.setSqlBuilder(builder);
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
	
	@Override
	public AsyncService createAsyncService() {
		return new AsyncServiceImpl();
	}
	
	/**
	 * @param serviceName the name of service
	 * @param prop the properties to look up
	 * @return the service
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
			throw new IllegalArgumentException("Failed to load service ", ne);
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

