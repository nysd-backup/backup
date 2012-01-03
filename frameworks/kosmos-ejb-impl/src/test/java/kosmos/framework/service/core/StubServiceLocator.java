/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.query.LimitedOrmQueryFactory;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.jpqlclient.api.free.EclipseLinkQueryFactoryImpl;
import kosmos.framework.jpqlclient.internal.free.impl.InternalEclipseLinkNativeQueryImpl;
import kosmos.framework.jpqlclient.internal.free.impl.InternalNamedQueryImpl;
import kosmos.framework.jpqlclient.internal.orm.impl.InternalOrmJpaQueryImpl;
import kosmos.framework.service.core.activation.AbstractServiceLocator;
import kosmos.framework.service.core.exception.ApplicationException;
import kosmos.framework.service.core.query.LimitedOrmQueryFactoryImpl;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.sqlclient.api.orm.OrmQueryFactoryImpl;
import kosmos.framework.sqlengine.builder.impl.SQLBuilderProxyImpl;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StubServiceLocator extends AbstractServiceLocator{

	public StubServiceLocator(Properties remotingProperties) {
		super(remotingProperties);	
		delegate = this;
	}
	
	/**
	 * @see kosmos.framework.service.core.activation.ServiceLocator#createContext()
	 */
	@Override
	public ServiceContext createServiceContext() {
		return new ServiceTestContextImpl();
	}

	/**
	 * @see kosmos.framework.service.core.activation.AbstractServiceLocator#lookup(java.lang.String, java.util.Properties)
	 */
	@Override
	protected Object lookup(String serviceName, Properties prop){
		
		String test = String.format("java:global/test/test-classes/%s",serviceName);		
		String format = String.format("java:global/test/classes/%s",serviceName);		
		try{
			return lookupFormat(test,prop);
		}catch(Exception e){
			return lookupFormat(format,prop);
		}

	}
	
	/**
	 * @param format
	 * @param prop
	 * @return
	 */
	private Object lookupFormat(String format, Properties prop){
		try{		
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
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		EclipseLinkQueryFactoryImpl factory = new EclipseLinkQueryFactoryImpl();
		InternalNamedQueryImpl named = new InternalNamedQueryImpl();
		named.setEntityManagerProvider(createEntityManagerProvider());
		named.setSqlBuilder(new SQLBuilderProxyImpl());
		
		InternalEclipseLinkNativeQueryImpl nativeQuery = new InternalEclipseLinkNativeQueryImpl();
		nativeQuery.setEntityManagerProvider(createEntityManagerProvider());
		nativeQuery.setSqlBuilder(new SQLBuilderProxyImpl());
		factory.setInternalQuery(nativeQuery);
		
		return factory;	
	}

	/**
	 * @see kosmos.framework.service.core.define.ComponentBuilder#createOrmQueryFactory()
	 */
	@Override
	public LimitedOrmQueryFactory createOrmQueryFactory() {
		LimitedOrmQueryFactoryImpl impl = new LimitedOrmQueryFactoryImpl();
		OrmQueryFactoryImpl internal = new OrmQueryFactoryImpl();
		InternalOrmJpaQueryImpl dao = new InternalOrmJpaQueryImpl();
		dao.setEntityManagerProvider(createEntityManagerProvider());	
		internal.setInternalOrmQuery(dao);
		impl.setInternalFactory(internal);
		return impl;
	}
	
	/**
	 * @return EntityManagerProvider
	 */
	protected EntityManagerProvider createEntityManagerProvider() {
		return lookupByInterface(EntityManagerProvider.class);
	}

	/**
	 * @see kosmos.framework.core.activation.ComponentLocator#createBusinessException()
	 */
	@Override
	public BusinessException createBusinessException() {
		return new ApplicationException();
	}

}
