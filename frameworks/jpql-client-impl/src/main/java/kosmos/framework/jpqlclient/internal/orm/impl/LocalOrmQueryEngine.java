/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm.impl;

import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.jpqlclient.api.orm.JPAOrmCondition;
import kosmos.framework.jpqlclient.api.orm.JPAOrmQuery;
import kosmos.framework.jpqlclient.internal.orm.GenericDao;
import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.orm.SortKey;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;


/**
 * The ORM query engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalOrmQueryEngine<T> implements JPAOrmQuery<T>{

	/** the GenericDao */
	private GenericDao dao;
	
	/** the condition */
	protected JPAOrmCondition<T> condition;
	
	/**
	 * @param entityClass the entityClass to set
	 */
	public LocalOrmQueryEngine(Class<T> entityClass){
		condition = new JPAOrmCondition<T>(entityClass);		
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.orm.JPAOrmQuery#setCondition(kosmos.framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public JPAOrmQuery<T> setCondition(JPAOrmCondition<T> condition) {
		this.condition = condition;
		return this;
	}

	/**
	 * @param dao the DAO to set
	 * @return self
	 */
	public LocalOrmQueryEngine<T> setDao(GenericDao dao){
		this.dao = dao;
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <Q extends Query> Q enableNoDataError() {
		condition.setNoDataErrorEnabled();
		return (Q)this;
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.api.orm.JPAOrmQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public JPAOrmQuery<T> setHint(String key, Object value){
		condition.setHint(key, value);
		return this;
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.orm.JPAOrmQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public JPAOrmQuery<T> setLockMode(LockModeType lockModeType) {
		condition.setLockModeType(lockModeType);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <Q extends Query> Q setMaxResults(int arg0) {
		condition.setMaxSize(arg0);
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <Q extends Query> Q setFirstResult(int arg0) {
		condition.setFirstResult(arg0);
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> eq(String column , Object value ){
		return setOperand(column, value, WhereOperand.Equal);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gt(String column , Object value ){
		return setOperand(column, value, WhereOperand.GreaterThan);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> lt(String column , Object value ){
		return setOperand(column, value, WhereOperand.LessThan);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gtEq(String column , Object value ){
		return setOperand(column, value, WhereOperand.GreaterEqual);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> ltEq(String column , Object value ){
		return setOperand(column, value, WhereOperand.LessEqual);
	}
	
	/**
	 * @param column カラム
	 * @param value 値
	 * @param operand 演算孁E
	 * @return
	 */
	private OrmQuery<T> setOperand(String column, Object value,WhereOperand operand) {
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,operand,value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> between(String column, Object from , Object to ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.Between,from,to));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmQuery<T> contains(String column, List<?> value) {
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.IN,value));
		return this;
	}
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> asc(String column){
		condition.getSortKeys().add(new SortKey(true,column));
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> desc(String column){
		condition.getSortKeys().add(new SortKey(false,column));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks) {
		return dao.find(condition,pks);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public T findAny() {
		return dao.findAny(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<T> getResultList() {
		return dao.getResultList(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public T getSingleResult() {
		setMaxResults(1);
		List<T> result = dao.getResultList(condition);
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks){
		Object result = dao.find(condition,pks);
		return result != null;	
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny(){
		Object result = dao.findAny(condition);
		return result != null;	
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists(){
		setMaxResults(1);
		List<T> result = dao.getResultList(condition);
		return ! result.isEmpty();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		throw new UnsupportedOperationException("do you realy need to use this method?");
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#filter(java.lang.String)
	 */
	@Override
	public OrmQuery<T> filter(String filterString) {
		condition.setFilterString(filterString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params){
		setMaxResults(1);
		List<T> result = list(params);
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params){
		condition.setEasyParams(params);
		return dao.getResultList(condition);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#findAny(java.lang.Object[])
	 */
	@Override
	public T findAny(Object... params) {
		condition.setEasyParams(params);
		return dao.findAny(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#order(java.lang.String)
	 */
	@Override
	public OrmQuery<T> order(String orderString) {
		condition.setOrderString(orderString);
		return this;
	}

}
