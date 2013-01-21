/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria;

import java.util.Map;

import alpha.query.criteria.strategy.QueryStatementBuilder;
import alpha.query.free.ModifyingConditions;


/**
 * The condition to update.
 *
 * @author yoshida-n
 * @version	created.
 */
public class CriteriaModifyingConditions<T> extends CriteriaConditions<T>{

	/**
	 * @param entityClass the entityClass to set
	 */
	public CriteriaModifyingConditions(Class<T> entityClass) {
		super(entityClass);
	}

	/**
	 * Creates the update Statement
	 * @return query string
	 */
	public ModifyingConditions buildUpdate(QueryStatementBuilder builder){
		String sql = builder.withUpdate(getEntityClass()).withSet(getCurrentValues()).withWhere(getConditions()).build();
		ModifyingConditions conditions =createModifyingConditions(sql,getEntityClass().getSimpleName()+".delete");
		//set
		for(Map.Entry<String, Object> v: getCurrentValues().entrySet()){
			if(!(v.getValue() instanceof FixString)){
				conditions.getParam().put(v.getKey(),v.getValue());
			}
		}
		return conditions;
	}
	
	/**
	 * Creates the delete Statement
	 * @return query string
	 */
	public ModifyingConditions buildDelete(QueryStatementBuilder builder){
		String sql = builder.withDelete(getEntityClass()).withWhere(getConditions()).build();
		return createModifyingConditions(sql,getEntityClass().getSimpleName()+".delete");
	}
	
	/**
	 * Creates the delete Statement
	 * @return query string
	 */
	public ModifyingConditions buildInsert(QueryStatementBuilder builder){
		String sql = builder.withInsert(getEntityClass(),getCurrentValues()).build();
		return createModifyingConditions(sql,getEntityClass().getSimpleName()+".insert");
	}
	
	/**
	 * Creates the conditions
	 * @param sql the sql
	 * @return conditions
	 */
	private ModifyingConditions createModifyingConditions(String sql,String id){
		ModifyingConditions parameter = new ModifyingConditions();
		parameter.setEntityManager(getEntityManager());
		parameter.setSql(sql);
		parameter.setQueryId(id);
		for(Criteria<?> criteria :getConditions()){
			criteria.accept(parameter);
		}
		for(Map.Entry<String, Object> e: getHints().entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		
		return parameter;
	}
	
}
