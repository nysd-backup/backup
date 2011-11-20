/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.core.query.EasyUpdate;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.query.StrictUpdate;
import kosmos.framework.core.query.impl.DefaultEasyQuery;
import kosmos.framework.core.query.impl.DefaultEasyUpdate;
import kosmos.framework.core.query.impl.DefaultStrictQuery;
import kosmos.framework.core.query.impl.DefaultStrictUpdate;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQueryFactory;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;

/**
 * The factory to create ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class AdvancedOrmQueryFactoryImpl implements AdvancedOrmQueryFactory{
	
	/** the internal factory */
	private OrmQueryFactory internalFactory;
	
	/**
	 * @param factory the factory to set
	 */
	public void setInternalFactory(OrmQueryFactory factory){
		this.internalFactory = factory;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQueryFactory#createStrictQuery(java.lang.Class)
	 */
	@Override
	public <T> StrictQuery<T> createStrictQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		StrictQuery<T> query = new DefaultStrictQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQueryFactory#createEasyQuery(java.lang.Class)
	 */
	@Override
	public <T> EasyQuery<T> createEasyQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		EasyQuery<T> query = new DefaultEasyQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQueryFactory#createStrictUpdate(java.lang.Class)
	 */
	@Override
	public <T> StrictUpdate<T> createStrictUpdate(Class<T> entityClass) {		
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		StrictUpdate<T> query = new DefaultStrictUpdate<T>(internalQuery);
		return query;
	}

	/**
	 * @see kosmos.framework.core.query.AdvancedOrmQueryFactory#createEasyUpdate(java.lang.Class)
	 */
	@Override
	public <T> EasyUpdate<T> createEasyUpdate(Class<T> entityClass) {
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		EasyUpdate<T> query = new DefaultEasyUpdate<T>(internalQuery);
		return query;
	}

}
