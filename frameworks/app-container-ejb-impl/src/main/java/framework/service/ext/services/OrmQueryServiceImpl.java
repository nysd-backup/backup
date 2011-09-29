/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.jpqlclient.api.PersistenceHints;
import framework.service.core.services.AbstractOrmQueryService;
import framework.service.ext.locator.ServiceLocatorImpl;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class OrmQueryServiceImpl<T extends AbstractEntity> extends AbstractOrmQueryService<T> implements OrmQueryService<T>{

	/**
	 * @see framework.service.core.services.AbstractOrmQueryService#getQueryFactory()
	 */
	@Override
	protected AdvancedOrmQueryFactory getQueryFactory() {
		return ServiceLocatorImpl.getComponentBuilder().createOrmQueryFactory();
	}

	/**
	 * @see framework.service.core.services.AbstractOrmQueryService#createStrictQuery(framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	protected StrictQuery<T> createStrictQuery(OrmCondition<T> request) {
		//永続化コンテキストを使用しない。
		StrictQuery<T> query = super.createStrictQuery(request);
		query.setHint(PersistenceHints.CACHE_STORE_MODE, CacheStoreMode.BYPASS);
		query.setHint(PersistenceHints.CACHE_RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
		return query;
	}

}
