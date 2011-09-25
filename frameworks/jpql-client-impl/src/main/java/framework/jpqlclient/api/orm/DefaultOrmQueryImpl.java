/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.orm;

import java.util.List;

import javax.persistence.LockModeType;

import framework.sqlclient.api.Query;
import framework.sqlclient.api.orm.OrmQuery;

/**
 * デフォルト提供のORMクエリ.
 * 開発者向けAPIはこのまま提供しないことが前提。
 * ラッパーを作成してeq、ascなどの条件指定はタイプセーフにすることが望ましい。
 *
 * @author yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public class DefaultOrmQueryImpl<T> implements JPAOrmQuery<T>{
	
	/** クエリ */
	private JPAOrmQuery<T> delegate;

	/**
	 * @param delegate クエリ
	 */
	public DefaultOrmQueryImpl(JPAOrmQuery<T> delegate){
		this.delegate = delegate;
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public JPAOrmQuery<T> setHint(String key, Object value) {
		delegate.setHint(key, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> eq(String column, Object value) {
		delegate.eq(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gt(String column, Object value) {
		delegate.gt(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> lt(String column, Object value) {
		delegate.lt(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gtEq(String column, Object value) {
		delegate.gtEq(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> ltEq(String column, Object value) {
		delegate.ltEq(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> between(String column, Object from, Object to) {
		delegate.between(column, from,to);
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmQuery<T> contains(String column, List<?> value) {
		delegate.contains(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> asc(String column) {
		delegate.asc(column);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> desc(String column) {
		delegate.desc(column);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks) {
		return delegate.find(pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public T findAny() {
		return delegate.findAny();
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<T> getResultList() {
		return delegate.getResultList();
	}

	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public T getSingleResult() {
		return (T)delegate.getSingleResult();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return delegate.exists(pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny() {
		return delegate.existsByAny();
	}
	
	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <Q extends Query> Q enableNoDataError() {
		delegate.enableNoDataError();
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <Q extends Query> Q setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <Q extends Query> Q setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return delegate.exists();
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public JPAOrmQuery<T> setLockMode(LockModeType type) {
		delegate.setLockMode(type);
		return this;
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmQuery#setCondition(framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public JPAOrmQuery<T> setCondition(JPAOrmCondition<T> condition) {
		delegate.setCondition(condition);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#filter(java.lang.String)
	 */
	@Override
	public OrmQuery<T> filter(String filterString) {
		delegate.filter(filterString);
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params){
		return delegate.single(params);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params){
		return delegate.list(params);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny(java.lang.Object[])
	 */
	@Override
	public T findAny(Object... params) {
		return delegate.findAny(params);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#order(java.lang.String)
	 */
	@Override
	public OrmQuery<T> order(String orderString) {
		delegate.order(orderString);
		return this;
	}


	
}
