/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;


/**
 *　The ORM updating engine.
 *
 * @author yoshida-n
 * @version	created.
 */
public class LocalOrmUpdateEngine<T> implements OrmUpdate<T>{

	/** the InternalOrmQuery */
	private final InternalOrmQuery dao;
	
	/** the condition */
	protected OrmContext<T> condition;
	
	/** the set statement */
	protected final Map<String,Object> set;
	
	/**
	 * @param entityClass the entityClass to set
	 */
	public LocalOrmUpdateEngine(Class<T> entityClass,InternalOrmQuery dao){
		condition = new OrmContext<T>(entityClass);
		set = new LinkedHashMap<String, Object>();	
		this.dao = dao;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#setCondition(kosmos.framework.sqlclient.api.orm.OrmContext)
	 */
	@Override
	public OrmUpdate<T> setCondition(OrmContext<T> condition) {
		this.condition = condition;
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrmUpdate<T> setHint(String key, Object value){
		condition.setHint(key, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> eq(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.Equal,value));
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gt(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.GreaterThan,value));
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> lt(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.LessThan,value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> gtEq(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.GreaterEqual,value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> ltEq(String column , Object value ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.LessEqual,value));
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> between(String column, Object from , Object to ){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.Between,from,to));
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#contains(java.lang.String, java.util.List)
	 */
	@Override
	public OrmUpdate<T> contains(String column, List<?> value){
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,WhereOperand.IN,value));
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmUpdate<T> set(String column, Object value) {
		set.put(column, value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return dao.update(condition, set);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#execute(java.util.List, java.lang.Object[])
	 */
	@Override
	public int execute(List<Object> sets,Object... value){
		List<String> a = new ArrayList<String>(set.keySet());
		for(int i = 0 ; i < sets.size(); i++){
			set.put(a.get(i),sets.get(i));
		}
		condition.setEasyParams(value);
		return dao.update(condition, set);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#set(java.lang.String[])
	 */
	@Override
	public OrmUpdate<T> set(String... setColumn) {
		for(String s : setColumn){
			set.put(s, null);
		}
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmUpdate#filter(java.lang.String)
	 */
	@Override
	public OrmUpdate<T> filter(String filterString) {
		condition.setFilterString(filterString);
		return this;
	}

}
