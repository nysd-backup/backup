/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.query.orm.EasyQuery;
import kosmos.framework.api.query.orm.EasyUpdate;
import kosmos.framework.api.query.orm.StrictQuery;
import kosmos.framework.api.query.orm.StrictUpdate;
import kosmos.framework.api.query.orm.impl.DefaultEasyQuery;
import kosmos.framework.api.query.orm.impl.DefaultEasyUpdate;
import kosmos.framework.api.query.orm.impl.DefaultStrictQuery;
import kosmos.framework.api.query.orm.impl.DefaultStrictUpdate;
import kosmos.framework.core.entity.AbstractEntity;
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
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createStrictQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictQuery<T> createStrictQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		StrictQuery<T> query = new DefaultStrictQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createEasyQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyQuery<T> createEasyQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		EasyQuery<T> query = new DefaultEasyQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createStrictUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictUpdate<T> createStrictUpdate(Class<T> entityClass) {		
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		StrictUpdate<T> query = new DefaultStrictUpdate<T>(internalQuery);
		return query;
	}

	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createEasyUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyUpdate<T> createEasyUpdate(Class<T> entityClass) {
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		EasyUpdate<T> query = new DefaultEasyUpdate<T>(internalQuery);
		return query;
	}

}
