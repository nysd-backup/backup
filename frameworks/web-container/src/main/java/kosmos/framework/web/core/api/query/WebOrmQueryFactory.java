/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.query;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.query.orm.EasyQuery;
import kosmos.framework.api.query.orm.EasyUpdate;
import kosmos.framework.api.query.orm.StrictQuery;
import kosmos.framework.api.query.orm.StrictUpdate;
import kosmos.framework.api.query.orm.impl.DefaultEasyQuery;
import kosmos.framework.api.query.orm.impl.DefaultStrictQuery;
import kosmos.framework.api.query.services.OrmQueryService;
import kosmos.framework.core.entity.AbstractEntity;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.web.core.api.service.ServiceCallable;
import kosmos.framework.web.core.api.service.ServiceFacade;

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
	
	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createStrictQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictQuery<T> createStrictQuery(Class<T> entityClass) {
		OrmQuery<T> delegate = new WebOrmQueryEngine<T>(service,entityClass);
		StrictQuery<T> query = new DefaultStrictQuery<T>(delegate);
		return query;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createEasyQuery(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyQuery<T> createEasyQuery(Class<T> entityClass) {
		OrmQuery<T> delegate = new WebOrmQueryEngine<T>(service,entityClass);
		EasyQuery<T> query = new DefaultEasyQuery<T>(delegate);
		return query;
	}

	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createStrictUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> StrictUpdate<T> createStrictUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.api.query.orm.AdvancedOrmQueryFactory#createEasyUpdate(java.lang.Class)
	 */
	@Override
	public <T extends AbstractEntity> EasyUpdate<T> createEasyUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}
	
}
