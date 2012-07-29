/**
 * Copyright 2011 the original author
 */
package client.sql.orm.strategy;

import java.util.List;
import java.util.Map;

import client.sql.free.FreeModifyQueryParameter;
import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeReadQueryParameter;
import client.sql.free.strategy.InternalQuery;
import client.sql.orm.CriteriaModifyQueryParameter;
import client.sql.orm.CriteriaQueryParameter;
import client.sql.orm.CriteriaReadQueryParameter;
import client.sql.orm.ExtractionCriteria;
import client.sql.orm.FixString;


/**
 * The internal ORM query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InternalOrmQueryImpl implements InternalOrmQuery{
	
	/** the SQLStatementBuilder */
	private SQLStatementBuilder sb = null;
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}

	/**
	 * @param sb the sb to set
	 */
	public void setSqlStatementBuilder(SQLStatementBuilder sb){
		this.sb = sb;
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#getResultList(client.sql.orm.CriteriaReadQueryParameter)
	 */
	@Override
	public <E> List<E> getResultList(CriteriaReadQueryParameter<E> condition) {
		FreeReadQueryParameter parameter = createParameter(condition);
		return internalQuery.getResultList(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#update(client.sql.orm.CriteriaReadQueryParameter, java.util.Map)
	 */
	@Override
	public int update(CriteriaModifyQueryParameter<?> condition) {		
		String sql = sb.createUpdate(condition.getEntityClass(),condition.getConditions(), condition.getCurrentValues());		
		FreeModifyQueryParameter parameter = createParameter(condition,sql,condition.getEntityClass().getName()+".update");			
		
		// for set statement
		for(Map.Entry<String, Object> v: condition.getCurrentValues().entrySet()){
			if(!(v.getValue() instanceof FixString)){
				parameter.getParam().put(v.getKey(),v.getValue());
			}
		}
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#delete(client.sql.orm.CriteriaReadQueryParameter)
	 */
	@Override
	public int delete(CriteriaModifyQueryParameter<?> condition) {
		String sql = sb.createDelete(condition.getEntityClass(),condition.getConditions());
		FreeModifyQueryParameter parameter = createParameter(condition,sql,condition.getEntityClass().getName()+".delete");	
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#getFetchResult(client.sql.orm.CriteriaReadQueryParameter, client.sql.free.QueryCallback)
	 */
	@Override
	public <E> List<E> getFetchResult(CriteriaReadQueryParameter<E> condition) {
		FreeReadQueryParameter parameter = createParameter(condition);
		return internalQuery.getFetchResult(parameter);
	}
	
	/**
	 * Creates the updating parameter
	 * @param condition
	 * @param sql
	 * @param sqlId
	 * @return
	 */
	private <E> FreeModifyQueryParameter createParameter(CriteriaModifyQueryParameter<E> condition,String sql,String sqlId){
		FreeModifyQueryParameter parameter = new FreeModifyQueryParameter();
		parameter.setEntityManager(condition.getEntityManager());
		parameter.setSql(sql);
		parameter.setQueryId(sqlId);
		setCondition(condition,parameter);
		setHint(condition.getHints(),parameter);
		return parameter;
		
	}
	
	/**
	 * Creates the selecting parameter
	 * @param condition the condition
	 * @return the parameter
	 */
	private <E> FreeReadQueryParameter createParameter(CriteriaReadQueryParameter<E> condition){
		String sql = sb.createSelect(condition);
		final FreeReadQueryParameter parameter = new FreeReadQueryParameter();		
		parameter.setEntityManager(condition.getEntityManager());
		parameter.setLockMode(condition.getLockModeType());
		parameter.setSql(sql);
		parameter.setResultType(condition.getEntityClass());
		parameter.setQueryId(condition.getEntityClass().getName()+".select");
		setCondition(condition,parameter);
		setHint(condition.getHints(),parameter);
		parameter.setFirstResult( condition.getFirstResult());			
		parameter.setMaxSize(condition.getMaxSize());
		return parameter;
	}
	
	/**
	 * Sets the hints.
	 * 
	 * @param hints the hints 
	 * @param engine the engine
	 */
	private void setHint(Map<String,Object> hints,FreeQueryParameter parameter){
		for(Map.Entry<String, Object> e: hints.entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
	}
	
	/**
	 * Sets the parameter.
	 * @param condition
	 * @param parameter
	 */
	private void setCondition(CriteriaQueryParameter<?> condition , final FreeQueryParameter parameter){
		for(ExtractionCriteria<?> criteria :condition.getConditions()){
			criteria.accept(parameter);
		}
	}


}
