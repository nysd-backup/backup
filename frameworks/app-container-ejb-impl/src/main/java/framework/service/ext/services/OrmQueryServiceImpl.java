/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

import org.eclipse.persistence.config.QueryHints;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.service.core.services.AbstractOrmQueryService;
import framework.service.ext.locator.ServiceLocatorImpl;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * 繝ｪ繝｢繝ｼ繝医°繧峨・ORM繧ｯ繧ｨ繝ｪ螳溯｡・
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
		//豌ｸ邯壼喧繧ｳ繝ｳ繝・く繧ｹ繝医・繧ｭ繝｣繝・す繝･繧剃ｽｿ逕ｨ縺励↑縺・
		StrictQuery<T> query = super.createStrictQuery(request);
		query.setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.BYPASS);
		query.setHint(QueryHints.CACHE_RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
		return query;
	}

}
