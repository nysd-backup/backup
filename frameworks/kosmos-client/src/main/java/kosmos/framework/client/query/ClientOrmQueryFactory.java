/**
 * Copyright 2011 the original author
 */
package kosmos.framework.client.query;

import kosmos.framework.core.query.LimitedOrmQueryFactory;
import kosmos.framework.core.query.EasyQuery;
import kosmos.framework.core.query.EasyUpdate;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.query.StrictUpdate;
import kosmos.framework.core.query.impl.DefaultEasyQuery;
import kosmos.framework.core.query.impl.DefaultStrictQuery;
import kosmos.framework.core.services.OrmQueryService;
import kosmos.framework.sqlclient.api.orm.OrmQuery;

/**
 * The ORM Query Service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ClientOrmQueryFactory implements LimitedOrmQueryFactory{
	
	/** the Facade */
	protected OrmQueryService service;
	
	/**
	 * @param service the service to set
	 */
	public void setOrmQueryService(OrmQueryService service){
		this.service = service;
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmQueryFactory#createStrictQuery(java.lang.Class)
	 */
	@Override
	public <T> StrictQuery<T> createStrictQuery(Class<T> entityClass) {
		OrmQuery<T> delegate = new ClientOrmQueryEngine<T>(service,entityClass);
		StrictQuery<T> query = new DefaultStrictQuery<T>(delegate);
		return query;
	}
	
	/**
	 * @see kosmos.framework.core.query.LimitedOrmQueryFactory#createEasyQuery(java.lang.Class)
	 */
	@Override
	public <T> EasyQuery<T> createEasyQuery(Class<T> entityClass) {
		OrmQuery<T> delegate = new ClientOrmQueryEngine<T>(service,entityClass);
		EasyQuery<T> query = new DefaultEasyQuery<T>(delegate);
		return query;
	}

	/**
	 * @see kosmos.framework.core.query.LimitedOrmQueryFactory#createStrictUpdate(java.lang.Class)
	 */
	@Override
	public <T> StrictUpdate<T> createStrictUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.core.query.LimitedOrmQueryFactory#createEasyUpdate(java.lang.Class)
	 */
	@Override
	public <T> EasyUpdate<T> createEasyUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}
	
}
