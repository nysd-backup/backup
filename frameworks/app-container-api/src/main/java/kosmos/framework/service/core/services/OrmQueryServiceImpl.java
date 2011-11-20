/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import java.util.List;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.services.OrmQueryService;
import kosmos.framework.jpqlclient.api.PersistenceHints;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.sqlclient.api.orm.OrmCondition;


/**
 * The ORM query.	
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryServiceImpl<T> implements OrmQueryService<T>{
	
	/**
	 * @return the factory to create the query
	 */
	protected AdvancedOrmQueryFactory getQueryFactory(){
		return ServiceLocator.createDefaultOrmQueryFactory();
	}

	/**
	 * @see kosmos.framework.core.services.OrmQueryService#find(kosmos.framework.sqlclient.api.orm.OrmCondition, java.lang.Object[])
	 */
	@Override
	public T find(OrmCondition<T> request, Object[] pks) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.find(pks);
	}

	/**
	 * @see kosmos.framework.core.services.OrmQueryService#findAny(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public T findAny(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.findAny();
	}

	/**
	 * @see kosmos.framework.core.services.OrmQueryService#getResultList(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public List<T> getResultList(OrmCondition<T> request) {		
		StrictQuery<T> query = createStrictQuery(request);
		return query.getResultList();
	}

	/**
	 * @see kosmos.framework.core.services.OrmQueryService#getSingleResult(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public T getSingleResult(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.getSingleResult();
	}

	/**
	 * @see kosmos.framework.core.services.OrmQueryService#exists(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public boolean exists(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.exists();
	}

	/**
	 * @see kosmos.framework.core.services.OrmQueryService#exists(kosmos.framework.sqlclient.api.orm.OrmCondition, java.lang.Object[])
	 */
	@Override
	public boolean exists(OrmCondition<T> request, Object[] pks) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.exists(pks);
	}

	/**
	 * @see kosmos.framework.core.services.OrmQueryService#existsByAny(kosmos.framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public boolean existsByAny(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.existsByAny();
	}

	
	/**
	 * Creates the query.
	 * @param request the request
	 * @return the query
	 */
	@SuppressWarnings("unchecked")
	protected StrictQuery<T> createStrictQuery(OrmCondition<T> request) {
		StrictQuery<T> query = getQueryFactory().createStrictQuery(request.getEntityClass());		
		query = StrictQuery.class.cast(query.setCondition(request));
		query.setHint(PersistenceHints.CACHE_STORE_MODE, CacheStoreMode.BYPASS);
		query.setHint(PersistenceHints.CACHE_RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
		return query;
	}
	
}
