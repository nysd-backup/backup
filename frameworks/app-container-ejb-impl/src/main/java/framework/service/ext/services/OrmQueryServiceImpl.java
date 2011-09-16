/**
 * Use is subject to license terms.
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
 * リモートからのORMクエリ実行
 *
 * @author yoshida-n
 * @version	2011/05/16 created.
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
		//永続化コンテキストのキャッシュを使用しない
		StrictQuery<T> query = super.createStrictQuery(request);
		query.setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.BYPASS);
		query.setHint(QueryHints.CACHE_RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
		return query;
	}

}
