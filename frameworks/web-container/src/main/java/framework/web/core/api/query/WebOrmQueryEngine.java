/**
 * Use is subject to license terms.
 */
package framework.web.core.api.query;

import java.util.List;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.jpqlclient.api.orm.JPAOrmCondition;
import framework.sqlclient.api.Query;
import framework.sqlclient.api.orm.OrmCondition;
import framework.sqlclient.api.orm.OrmQuery;
import framework.sqlclient.api.orm.SortKey;
import framework.sqlclient.api.orm.WhereCondition;
import framework.sqlclient.api.orm.WhereOperand;

/**
 * WEBコンテナ用ORMクエリエンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public class WebOrmQueryEngine<E extends AbstractEntity> implements OrmQuery<E>{
	
	/** クエリの実行体 */
	private OrmQueryService<E> service;
	
	/**　DTO */
	private final OrmCondition<E> request;
	
	/**
	 * @param service
	 * @param entityClass
	 */
	WebOrmQueryEngine(OrmQueryService<E> service,Class<E> entityClass){
		this.service = service;
		this.request = new JPAOrmCondition<E>(entityClass);
	}

	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */	
	@Override
	public <T extends Query> T enableNoDataError() {
		request.setNoDataErrorEnabled();
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		request.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		request.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<E> getResultList() {
		return service.getResultList(request);
	}

	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public E getSingleResult() {
		return service.getSingleResult(request);
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return service.exists(request);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> eq(String column, Object value) {
		return setOperand(column, value, WhereOperand.Equal);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> gt(String column, Object value) {
		return setOperand(column, value, WhereOperand.GreaterThan);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> lt(String column, Object value) {
		return setOperand(column, value, WhereOperand.LessThan);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> gtEq(String column, Object value) {
		return setOperand(column, value, WhereOperand.GreaterEqual);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> ltEq(String column, Object value) {
		return setOperand(column, value, WhereOperand.LessEqual);
	}
	
	/**
	 * @param column カラム
	 * @param value 値
	 * @param operand 演算子
	 * @return
	 */
	private OrmQuery<E> setOperand(String column, Object value,WhereOperand operand) {
		request.getConditions().add(new WhereCondition(column,request.getConditions().size()+1,operand,value));
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> between(String column, Object from, Object to) {
		request.getConditions().add(new WhereCondition(column,request.getConditions().size()+1,WhereOperand.Between,from,to));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<E> asc(String column) {
		request.getSortKeys().add(new SortKey(true,column));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<E> desc(String column) {
		request.getSortKeys().add(new SortKey(false,column));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public E find(Object... pks) {
		return service.find(request, pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findWithLockNoWait(java.lang.Object[])
	 */
	@Override
	public E findWithLockNoWait(Object... pks) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public E findAny() {
		return service.findAny(request);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return service.exists(request,pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny() {
		return service.existsByAny(request);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmQuery<E> contains(String column, List<?> value) {
		setOperand(column, value, WhereOperand.IN);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#filter(java.lang.String)
	 */
	@Override
	public OrmQuery<E> filter(String filterString) {
		request.setFilterString(filterString);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#order(java.lang.String)
	 */
	@Override
	public OrmQuery<E> order(String orderString) {
		request.setOrderString(orderString);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
	 */
	@Override
	public E single(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
	 */
	@Override
	public List<E> list(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny(java.lang.Object[])
	 */
	@Override
	public E findAny(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
}
