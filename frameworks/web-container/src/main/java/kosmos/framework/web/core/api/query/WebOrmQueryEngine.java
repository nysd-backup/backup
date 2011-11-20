/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.query;

import java.util.List;

import kosmos.framework.core.services.OrmQueryService;
import kosmos.framework.jpqlclient.api.orm.JPAOrmCondition;
import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.orm.OrmCondition;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.orm.SortKey;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;

/**
 * The ORM query for WEB.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class WebOrmQueryEngine<E> implements OrmQuery<E>{
	
	/** the service */
	private OrmQueryService<E> service;
	
	/**　DTO */
	private final OrmCondition<E> request;
	
	/**
	 * @param service the service
	 * @param entityClass the entityClass
	 */
	WebOrmQueryEngine(OrmQueryService<E> service,Class<E> entityClass){
		this.service = service;
		this.request = new JPAOrmCondition<E>(entityClass);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#enableNoDataError()
	 */	
	@Override
	public <T extends Query> T enableNoDataError() {
		request.setNoDataErrorEnabled();
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		request.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		request.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<E> getResultList() {
		return service.getResultList(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public E getSingleResult() {
		return service.getSingleResult(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return service.exists(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> eq(String column, Object value) {
		return setOperand(column, value, WhereOperand.Equal);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> gt(String column, Object value) {
		return setOperand(column, value, WhereOperand.GreaterThan);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> lt(String column, Object value) {
		return setOperand(column, value, WhereOperand.LessThan);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> gtEq(String column, Object value) {
		return setOperand(column, value, WhereOperand.GreaterEqual);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> ltEq(String column, Object value) {
		return setOperand(column, value, WhereOperand.LessEqual);
	}
	
	/**
	 * @param column the column
	 * @param value the value
	 * @param operand the operand
	 * @return self
	 */
	private OrmQuery<E> setOperand(String column, Object value,WhereOperand operand) {
		request.getConditions().add(new WhereCondition(column,request.getConditions().size()+1,operand,value));
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<E> between(String column, Object from, Object to) {
		request.getConditions().add(new WhereCondition(column,request.getConditions().size()+1,WhereOperand.Between,from,to));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<E> asc(String column) {
		request.getSortKeys().add(new SortKey(true,column));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<E> desc(String column) {
		request.getSortKeys().add(new SortKey(false,column));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public E find(Object... pks) {
		return service.find(request, pks);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public E findAny() {
		return service.findAny(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return service.exists(request,pks);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny() {
		return service.existsByAny(request);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmQuery<E> contains(String column, List<?> value) {
		setOperand(column, value, WhereOperand.IN);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#filter(java.lang.String)
	 */
	@Override
	public OrmQuery<E> filter(String filterString) {
		request.setFilterString(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#order(java.lang.String)
	 */
	@Override
	public OrmQuery<E> order(String orderString) {
		request.setOrderString(orderString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
	 */
	@Override
	public E single(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
	 */
	@Override
	public List<E> list(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#findAny(java.lang.Object[])
	 */
	@Override
	public E findAny(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
}
