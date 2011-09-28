/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.EasyQuery;
import framework.api.query.orm.EasyUpdate;
import framework.api.query.orm.StrictQuery;
import framework.api.query.orm.StrictUpdate;
import framework.api.query.orm.impl.DefaultEasyQuery;
import framework.api.query.orm.impl.DefaultEasyUpdate;
import framework.api.query.orm.impl.DefaultStrictQuery;
import framework.api.query.orm.impl.DefaultStrictUpdate;
import framework.core.entity.AbstractEntity;
import framework.sqlclient.api.orm.OrmQuery;
import framework.sqlclient.api.orm.OrmQueryFactory;
import framework.sqlclient.api.orm.OrmUpdate;

/**
 * ORMクエリを生成すめE
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class AdvancedOrmQueryFactoryImpl implements AdvancedOrmQueryFactory{
	
	/** クエリファクトリ */
	private OrmQueryFactory internalFactory;
	
	/**
	 * @param factory ファクトリ
	 */
	public void setInternalFactory(OrmQueryFactory factory){
		this.internalFactory = factory;
	}
	
	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createStrictQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictQuery<T> createStrictQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		StrictQuery<T> query = new DefaultStrictQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createEasyQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyQuery<T> createEasyQuery(Class<T> entityClass) {		
		OrmQuery<T> internalQuery = internalFactory.createQuery(entityClass);
		EasyQuery<T> query = new DefaultEasyQuery<T>(internalQuery);
		return query;
	}
	
	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createStrictUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictUpdate<T> createStrictUpdate(Class<T> entityClass) {		
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		StrictUpdate<T> query = new DefaultStrictUpdate<T>(internalQuery);
		return query;
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createEasyUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyUpdate<T> createEasyUpdate(Class<T> entityClass) {
		OrmUpdate<T> internalQuery = internalFactory.createUpdate(entityClass);
		EasyUpdate<T> query = new DefaultEasyUpdate<T>(internalQuery);
		return query;
	}

}
