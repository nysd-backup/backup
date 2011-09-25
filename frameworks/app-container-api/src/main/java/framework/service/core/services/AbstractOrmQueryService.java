/**
 * Use is subject to license terms.
 */
package framework.service.core.services;

import java.util.List;
import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.orm.StrictQuery;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * ORMクエリ.	
 *
 * @author yoshida-n
 * @version	2011/05/16 created.
 */
public abstract class AbstractOrmQueryService<T extends AbstractEntity> implements OrmQueryService<T>{
	
	/**
	 * @return クエリファクトリ
	 */
	protected abstract AdvancedOrmQueryFactory getQueryFactory();

	/**
	 * @see framework.api.query.services.OrmQueryService#find(framework.sqlclient.api.orm.OrmCondition, java.lang.Object[])
	 */
	@Override
	public T find(OrmCondition<T> request, Object[] pks) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.find(pks);
	}

	/**
	 * @see framework.api.query.services.OrmQueryService#findAny(framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public T findAny(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.findAny();
	}

	/**
	 * @see framework.api.query.services.OrmQueryService#getResultList(framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public List<T> getResultList(OrmCondition<T> request) {		
		StrictQuery<T> query = createStrictQuery(request);
		return query.getResultList();
	}

	/**
	 * @see framework.api.query.services.OrmQueryService#getSingleResult(framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public T getSingleResult(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.getSingleResult();
	}

	/**
	 * @see framework.api.query.services.OrmQueryService#exists(framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public boolean exists(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.exists();
	}

	/**
	 * @see framework.api.query.services.OrmQueryService#exists(framework.sqlclient.api.orm.OrmCondition, java.lang.Object[])
	 */
	@Override
	public boolean exists(OrmCondition<T> request, Object[] pks) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.exists(pks);
	}

	/**
	 * @see framework.api.query.services.OrmQueryService#existsByAny(framework.sqlclient.api.orm.OrmCondition)
	 */
	@Override
	public boolean existsByAny(OrmCondition<T> request) {
		StrictQuery<T> query = createStrictQuery(request);
		return query.existsByAny();
	}

	
	/**
	 * クエリを作成する
	 * @param request リクエスト
	 * @return クエリ
	 */
	@SuppressWarnings("unchecked")
	protected StrictQuery<T> createStrictQuery(OrmCondition<T> request) {
		StrictQuery<T> query = getQueryFactory().createStrictQuery(request.getEntityClass());
		return StrictQuery.class.cast(query.setCondition(request));
	}
}
