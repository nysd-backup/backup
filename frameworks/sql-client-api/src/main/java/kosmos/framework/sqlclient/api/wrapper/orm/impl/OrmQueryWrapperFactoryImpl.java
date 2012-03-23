/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm.impl;

import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQueryFactory;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.EasyQuery;
import kosmos.framework.sqlclient.api.wrapper.orm.EasyUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.LightQuery;
import kosmos.framework.sqlclient.api.wrapper.orm.LightUpdate;
import kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapperFactory;

/**
 * The factory to create ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryWrapperFactoryImpl implements OrmQueryWrapperFactory{
	
	/** the internal factory */
	private OrmQueryFactory internalFactory;
	
	/**
	 * @param factory the factory to set
	 */
	public void setInternalFactory(OrmQueryFactory factory){
		this.internalFactory = factory;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapperFactory#createEasyQuery(java.lang.Class)
	 */
	@Override
	public <T> EasyQuery<T> createEasyQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		EasyQuery<T> query = new DefaultEasyQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapperFactory#createLightQuery(java.lang.Class)
	 */
	@Override
	public <T> LightQuery<T> createLightQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		LightQuery<T> query = new DefaultLightQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapperFactory#createEasyUpdate(java.lang.Class)
	 */
	@Override
	public <T> EasyUpdate<T> createEasyUpdate(Class<T> entityClass) {		
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		EasyUpdate<T> query = new DefaultEasyUpdate<T>(internalQuery);
		return query;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.orm.OrmQueryWrapperFactory#createLightUpdate(java.lang.Class)
	 */
	@Override
	public <T> LightUpdate<T> createLightUpdate(Class<T> entityClass) {
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		LightUpdate<T> query = new DefaultLightUpdate<T>(internalQuery);
		return query;
	}

}
