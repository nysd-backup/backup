/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.query;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.EasyQuery;
import framework.api.query.orm.EasyUpdate;
import framework.api.query.orm.StrictQuery;
import framework.api.query.orm.StrictUpdate;
import framework.api.query.orm.impl.DefaultEasyQuery;
import framework.api.query.orm.impl.DefaultStrictQuery;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.logics.builder.MessageAccessor;
import framework.sqlclient.api.orm.OrmQuery;
import framework.web.core.api.service.ServiceCallable;
import framework.web.core.api.service.ServiceFacade;

/**
 * WEB用のORMクエリファクトリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings({"rawtypes","unchecked"})
@ServiceCallable
public class WebOrmQueryFactory implements AdvancedOrmQueryFactory{
	
	/** ファサーチE*/
	@ServiceFacade
	protected OrmQueryService service;
	
	/** メチE��ージ */
	protected MessageAccessor accessor;

	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createStrictQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictQuery<T> createStrictQuery(Class<T> entityClass) {
		OrmQuery<T> delegate = new WebOrmQueryEngine<T>(service,entityClass);
		StrictQuery<T> query = new DefaultStrictQuery<T>(delegate);
		return query;
	}
	
	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createEasyQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyQuery<T> createEasyQuery(Class<T> entityClass) {
		OrmQuery<T> delegate = new WebOrmQueryEngine<T>(service,entityClass);
		EasyQuery<T> query = new DefaultEasyQuery<T>(delegate);
		return query;
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createStrictUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictUpdate<T> createStrictUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmQueryFactory#createEasyUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyUpdate<T> createEasyUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}
	
}
