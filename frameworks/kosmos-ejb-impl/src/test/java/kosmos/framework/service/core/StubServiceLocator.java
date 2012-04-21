/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.jpqlclient.free.strategy.InternalNamedQueryImpl;
import kosmos.framework.jpqlclient.free.strategy.InternalNativeQueryImpl;
import kosmos.framework.jpqlclient.orm.strategy.InternalOrmQueryImpl;
import kosmos.framework.service.core.activation.AbstractServiceLocator;
import kosmos.framework.service.core.advice.InternalPerfInterceptor;
import kosmos.framework.service.core.advice.InternalSQLBuilderInterceptor;
import kosmos.framework.service.core.advice.ProxyFactory;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.free.QueryFactory;
import kosmos.framework.sqlclient.free.strategy.InternalQuery;
import kosmos.framework.sqlclient.orm.OrmQueryFactory;
import kosmos.framework.sqlengine.builder.SQLBuilder;
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
