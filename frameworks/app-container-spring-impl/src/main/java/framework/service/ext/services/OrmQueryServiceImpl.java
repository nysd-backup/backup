/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.annotation.Resource;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.jpqlclient.api.PersistenceHints;
import framework.service.core.services.AbstractOrmQueryService;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class OrmQueryServiceImpl<T extends AbstractEntity> extends AbstractOrmQueryService<T> implements OrmQueryService<T>{
	
	/** the factory to create query */
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
		//永続化コンテキストを使用しない。
		StrictQuery<T> query = super.createStrictQuery(request);
		query.setHint(PersistenceHints.CACHE_STORE_MODE, CacheStoreMode.BYPASS);
		query.setHint(PersistenceHints.CACHE_RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
		return query;
	}

}
