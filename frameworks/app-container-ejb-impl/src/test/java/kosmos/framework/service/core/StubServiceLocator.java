/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.jpqlclient.api.free.EclipseLinkQueryFactoryImpl;
import kosmos.framework.jpqlclient.api.orm.OrmQueryFactoryImpl;
import kosmos.framework.jpqlclient.internal.orm.impl.GenericJPADaoImpl;
import kosmos.framework.service.core.locator.AbstractServiceLocator;
import kosmos.framework.service.core.query.AdvancedOrmQueryFactoryImpl;
import kosmos.framework.service.core.query.UnexpectedEmptyHandlerImpl;
import kosmos.framework.service.core.query.UnexpectedMultiResultHandlerImpl;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.api.free.QueryFactory;


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
	 * @see kosmos.framework.service.core.locator.ServiceLocator#createContext()
	 */
	@Override
	public ServiceContext createContext() {
		return new ServiceTestContextImpl();
	}

	/**
	 * @see kosmos.framework.service.core.locator.AbstractServiceLocator#lookup(java.lang.String, java.util.Properties)
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
		factory.setEmptyHandler( new UnexpectedEmptyHandlerImpl());
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
		dao.setEmptyHandler(new UnexpectedEmptyHandlerImpl());
		dao.setMultiResultHandler(new UnexpectedMultiResultHandlerImpl());
		internal.setGenericDao(dao);
		impl.setInternalFactory(internal);
		return impl;
	}
	
	/**
	 * @return EntityManagerProvider
	 */
	protected EntityManagerProvider createEntityManagerProvider() {
		return lookupByInterface(EntityManagerProvider.class);
	}
	
}
