/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.orm;

import framework.jpqlclient.api.orm.JPAOrmQuery;
import framework.jpqlclient.internal.orm.GenericDao;
import framework.jpqlclient.internal.orm.impl.LocalOrmQueryEngine;
import framework.jpqlclient.internal.orm.impl.LocalOrmUpdateEngine;
import framework.sqlclient.api.orm.OrmQuery;
import framework.sqlclient.api.orm.OrmQueryFactory;
import framework.sqlclient.api.orm.OrmUpdate;

/**
 * ORMクエリファクトリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrmQueryFactoryImpl implements OrmQueryFactory{
	
	/** Dao */
	private GenericDao genericDao;
	
	/**
	 * @param dao DAO
	 */
	public void setGenericDao(GenericDao dao){
		this.genericDao = dao;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,Q extends OrmQuery<T>> Q createQuery(Class<T> entityClass) {
		JPAOrmQuery<T> engine = new LocalOrmQueryEngine<T>(entityClass).setAccessor(genericDao);
		return (Q)create(engine);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.api.query.OrmQueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,Q extends OrmUpdate<T>> Q createUpdate(Class<T> entityClass){
		JPAOrmUpdate<T> engine = new LocalOrmUpdateEngine<T>(entityClass).setAccessor(genericDao);
		return (Q)create(engine);
	}
	
	/**
	 * @param <T> type
	 * @param <Q> ype
	 * @param engine エンジン
	 * @return クエリ
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JPAOrmUpdate create(JPAOrmUpdate engine ){
		return new DefaultOrmUpdateImpl(engine);
	}
	
	/**
	 * @param <T> type
	 * @param <Q> ype
	 * @param engine エンジン
	 * @return クエリ
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JPAOrmQuery create(JPAOrmQuery engine ){
		return new DefaultOrmQueryImpl(engine);
	}
	
}
