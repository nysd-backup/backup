/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.internal.orm.impl;

import java.util.List;

import javax.persistence.LockModeType;

import framework.jpqlclient.api.orm.JPAOrmCondition;
import framework.jpqlclient.api.orm.JPAOrmQuery;
import framework.jpqlclient.internal.orm.GenericDao;
import framework.sqlclient.api.Query;
import framework.sqlclient.api.orm.OrmQuery;
import framework.sqlclient.api.orm.SortKey;
import framework.sqlclient.api.orm.WhereCondition;
import framework.sqlclient.api.orm.WhereOperand;

/**
 * エンティティクエリ.
 *
 * @author yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public class LocalOrmQueryEngine<T> implements JPAOrmQuery<T>{

	/** 検索エンジン */
	private GenericDao dao;
	
	/** 検索条件 */
	protected JPAOrmCondition<T> condition;
	
	/**
	 * @param entityClass エンティティクラス
	 */
	public LocalOrmQueryEngine(Class<T> entityClass){
		condition = new JPAOrmCondition<T>(entityClass);		
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmQuery#setCondition(framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public JPAOrmQuery<T> setCondition(JPAOrmCondition<T> condition) {
		this.condition = condition;
		return this;
	}

	/**
	 * @param dao DAO
	 * @param accessor メッセージ
	 * @return self
	 */
	public LocalOrmQueryEngine<T> setAccessor(GenericDao dao){
		this.dao = dao;
		return this;
	}

	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <Q extends Query> Q enableNoDataError() {
		condition.setNoDataErrorEnabled();
		return (Q)this;
	}
	
	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public JPAOrmQuery<T> setHint(String key, Object value){
		condition.setHint(key, value);
		return this;
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmQuery#setLockModeType(javax.persistence.LockModeType)
	 */
	@Override
	public JPAOrmQuery<T> setLockMode(LockModeType lockModeType) {
		condition.setLockModeType(lockModeType);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <Q extends Query> Q setMaxResults(int arg0) {
		condition.setMaxSize(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <Q extends Query> Q setFirstResult(int arg0) {
		condition.setFirstResult(arg0);
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> eq(String column , Object value ){
		return setOperand(column, value, WhereOperand.Equal);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gt(String column , Object value ){
		return setOperand(column, value, WhereOperand.GreaterThan);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> lt(String column , Object value ){
		return setOperand(column, value, WhereOperand.LessThan);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gtEq(String column , Object value ){
		return setOperand(column, value, WhereOperand.GreaterEqual);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> ltEq(String column , Object value ){
		return setOperand(column, value, WhereOperand.LessEqual);
	}
	
	/**
	 * @param column カラム
	 * @param value 値
	 * @param operand 演算子
	 * @return
	 */
	private OrmQuery<T> setOperand(String column, Object value,WhereOperand operand) {
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,operand,value));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> between(String column, Object from , Object to ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.Between,from,to));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmQuery<T> contains(String column, List<?> value) {
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.IN,value));
		return this;
	}
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> asc(String column){
		condition.getSortKeys().add(new SortKey(true,column));
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> desc(String column){
		condition.getSortKeys().add(new SortKey(false,column));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks) {
		return dao.find(condition,pks);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findWithLockNoWait(java.lang.Object[])
	 */
	@Override
	public T findWithLockNoWait(Object... pks) {	
		return dao.findWithLock(condition,0,pks);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public T findAny() {
		return dao.findAny(condition);
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<T> getResultList() {
		return dao.getResultList(condition);
	}

	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
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
	 * @see framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks){
		Object result = dao.find(condition,pks);
		return result != null;	
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny(){
		Object result = dao.findAny(condition);
		return result != null;	
	}
	
	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists(){
		setMaxResults(1);
		List<T> result = dao.getResultList(condition);
		return ! result.isEmpty();
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		throw new UnsupportedOperationException("do you realy need to use this method?");
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#filter(java.lang.String)
	 */
	@Override
	public OrmQuery<T> filter(String filterString) {
		condition.setFilterString(filterString);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
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
	 * @see framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params){
		condition.setEasyParams(params);
		return dao.getResultList(condition);
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny(java.lang.Object[])
	 */
	@Override
	public T findAny(Object... params) {
		condition.setEasyParams(params);
		return dao.findAny(condition);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#order(java.lang.String)
	 */
	@Override
	public OrmQuery<T> order(String orderString) {
		condition.setOrderString(orderString);
		return this;
	}

}
