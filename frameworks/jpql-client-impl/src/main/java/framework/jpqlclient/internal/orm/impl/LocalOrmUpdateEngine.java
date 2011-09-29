/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.orm.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import framework.jpqlclient.api.orm.JPAOrmCondition;
import framework.jpqlclient.api.orm.JPAOrmUpdate;
import framework.jpqlclient.internal.orm.GenericDao;
import framework.sqlclient.api.orm.OrmUpdate;
import framework.sqlclient.api.orm.WhereCondition;
import framework.sqlclient.api.orm.WhereOperand;

/**
 *　The ORM updating engine.
 *
 * @author yoshida-n
 * @version	created.
 */
public class LocalOrmUpdateEngine<T> implements JPAOrmUpdate<T>{

	/** the GenericDao */
	private GenericDao dao;
	
	/** the condition */
	protected JPAOrmCondition<T> condition;
	
	/** the set statement */
	protected final Map<String,Object> set;
	
	/**
	 * @param entityClass the entityClass to set
	 */
	public LocalOrmUpdateEngine(Class<T> entityClass){
		condition = new JPAOrmCondition<T>(entityClass);
		set = new LinkedHashMap<String, Object>();	
	}

	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmUpdate#setCondition(framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public JPAOrmUpdate<T> setCondition(JPAOrmCondition<T> condition) {
		this.condition = condition;
		return this;
	}

	/**
	 * @param dao DAO
	 * @return self
	 */
	public LocalOrmUpdateEngine<T> setAccessor(GenericDao dao){
		this.dao = dao;
		return this;
	}
	
	/**
	 * @see framework.jpqlclient.api.orm.JPAOrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public JPAOrmUpdate<T> setHint(String key, Object value){
		condition.setHint(key, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> eq(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.Equal,value));
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gt(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.GreaterThan,value));
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> lt(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.LessThan,value));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gtEq(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.GreaterEqual,value));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> ltEq(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.LessEqual,value));
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> between(String column, Object from , Object to ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.Between,from,to));
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> set(String column, Object value) {
		set.put(column, value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return dao.updateAny(condition, set);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int execute(List<Object> sets,Object... value){
		List<String> a = new ArrayList<String>(set.keySet());
		for(int i = 0 ; i < sets.size(); i++){
			set.put(a.get(i),sets.get(i));
		}
		condition.setEasyParams(value);
		return dao.updateAny(condition, set);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String[])
	 */
	@Override
	public OrmUpdate<T> set(String... setColumn) {
		for(String s : setColumn){
			set.put(s, null);
		}
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmUpdate#filter(java.lang.String)
	 */
	@Override
	public OrmUpdate<T> filter(String filterString) {
		condition.setFilterString(filterString);
		return this;
	}

}
