/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.annotation.Resource;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

import org.eclipse.persistence.config.QueryHints;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.service.core.services.AbstractOrmQueryService;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * ORM繧ｯ繧ｨ繝ｪ.	
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class OrmQueryServiceImpl<T extends AbstractEntity> extends AbstractOrmQueryService<T> implements OrmQueryService<T>{
	
	/** 繧ｯ繧ｨ繝ｪ繝輔ぃ繧ｯ繝医Μ */
	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;

	/**
	 * @see framework.service.core.services.AbstractOrmQueryService#getQueryFactory()
	 */
	@Override
	protected AdvancedOrmQueryFactory getQueryFactory() {
		return ormQueryFactory;
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
