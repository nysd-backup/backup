/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

import java.util.Iterator;
import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;


/**
 * The default ORM query.
 * 
 * <pre>
 * Don't allow to use this.
 * Create the wrapper class for this.
 * the methods like eq and asc should be type safe.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class DefaultOrmQueryImpl<T> implements OrmQuery<T>{
	

	/** the InternalOrmQuery */
	private final InternalOrmQuery dao;
	
	/** the condition */
	protected OrmQueryParameter<T> condition;
	
	/**
	 * @param entityClass the entityClass to set
	 */
	public DefaultOrmQueryImpl(Class<T> entityClass,InternalOrmQuery dao){
		condition = new OrmQueryParameter<T>(entityClass);		
		this.dao = dao;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	public OrmQuery<T> setCondition(OrmQueryParameter<T> condition) {
		this.condition = (OrmQueryParameter<T>)condition;
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> setHint(String key, Object value){
		condition.setHint(key, value);
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
	 * @param column the column 
	 * @param value the value 
	 * @param operand the operand
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
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public long count() {
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
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#order(java.lang.String)
	 */
	@Override
	public OrmQuery<T> order(String orderString) {
		condition.setOrderString(orderString);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public OrmQuery<T> setLockMode(LockModeType type) {
		condition.setLockModeType(type);
		return this;
	}


	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#getCurrentParams()
	 */
	@Override
	public OrmQueryParameter<T> getCurrentParams() {
		return this.condition;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#getFetchResult(kosmos.framework.sqlclient.api.free.QueryCallback)
	 */
	@Override
	public long getFetchResult(QueryCallback<T> callback) {
		List<T> lazyList = getFetchResult();
		Iterator<T> iterator = lazyList.iterator();
		long count = 0;
		try{
			while(iterator.hasNext()){	
				callback.handleRow(iterator.next(), count);
				count++;
			}
		}finally{
			lazyList.clear();
		}
		return count;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#fetch(kosmos.framework.sqlclient.api.free.QueryCallback)
	 */
	@Override
	public long fetch(QueryCallback<T> callback,Object... params) {
		condition.setEasyParams(params);
		return getFetchResult(callback);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#getFetchResult()
	 */
	@Override
	public List<T> getFetchResult() {
		List<T> lazyList = dao.getFetchResult(condition);
		return lazyList;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#fetch(java.lang.Object[])
	 */
	@Override
	public List<T> fetch(Object... params) {
		condition.setEasyParams(params);
		return getFetchResult();
	}
	
}
