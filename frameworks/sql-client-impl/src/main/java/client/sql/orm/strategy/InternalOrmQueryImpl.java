/**
 * Copyright 2011 the original author
 */
package client.sql.orm.strategy;

import java.util.List;
import java.util.Map;

import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeSelectParameter;
import client.sql.free.FreeUpsertParameter;
import client.sql.free.strategy.InternalQuery;
import client.sql.orm.FixString;
import client.sql.orm.OrmQueryParameter;
import client.sql.orm.OrmSelectParameter;
import client.sql.orm.OrmUpdateParameter;
import client.sql.orm.strategy.SQLStatementBuilder.Bindable;


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
	 * @see client.sql.orm.strategy.InternalOrmQuery#find(java.lang.Class, java.util.Map, java.lang.Object[])
	 */
	@Override
	public <E> E find(OrmSelectParameter<E> context,Object... pks) {		
		Object v = pks;
		if( pks.length == 1){
			v = pks[0];
		}
		E result = null;
		if(context.getLockModeType() != null){
			result = (E)context.getEntityManager().find(context.getEntityClass(),v,context.getLockModeType(),context.getHints());
		}else{
			result = (E)context.getEntityManager().find(context.getEntityClass(),v,context.getHints()); 
		}
		return result;
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#getResultList(client.sql.orm.OrmSelectParameter)
	 */
	@Override
	public <E> List<E> getResultList(OrmSelectParameter<E> condition) {
		FreeSelectParameter parameter = createParameter(condition);
		return internalQuery.getResultList(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#update(client.sql.orm.OrmSelectParameter, java.util.Map)
	 */
	@Override
	public int update(OrmUpdateParameter<?> condition) {		
		String sql = sb.createUpdate(condition.getEntityClass(),condition.getConditions(), condition.getCurrentValues());		
		FreeUpsertParameter parameter = createParameter(condition,sql,condition.getEntityClass().getName()+".update");			
		
		// for set statement
		for(Map.Entry<String, Object> v: condition.getCurrentValues().entrySet()){
			if(!(v.getValue() instanceof FixString)){
				parameter.getParam().put(v.getKey(),v.getValue());
			}
		}
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#delete(client.sql.orm.OrmSelectParameter)
	 */
	@Override
	public int delete(OrmUpdateParameter<?> condition) {
		String sql = sb.createDelete(condition.getEntityClass(),condition.getConditions());
		FreeUpsertParameter parameter = createParameter(condition,sql,condition.getEntityClass().getName()+".delete");	
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#getFetchResult(client.sql.orm.OrmSelectParameter, client.sql.free.QueryCallback)
	 */
	@Override
	public <E> List<E> getFetchResult(OrmSelectParameter<E> condition) {
		FreeSelectParameter parameter = createParameter(condition);
		return internalQuery.getFetchResult(parameter);
	}
	
	/**
	 * Creates the updating parameter
	 * @param condition
	 * @param sql
	 * @param sqlId
	 * @return
	 */
	private <E> FreeUpsertParameter createParameter(OrmUpdateParameter<E> condition,String sql,String sqlId){
		FreeUpsertParameter parameter = new FreeUpsertParameter();
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
	private <E> FreeSelectParameter createParameter(OrmSelectParameter<E> condition){
		String sql = sb.createSelect(condition);
		final FreeSelectParameter parameter = new FreeSelectParameter();		
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
	private void setCondition(OrmQueryParameter<?> condition , final FreeQueryParameter parameter){
		sb.setConditionParameters(condition.getConditions(), new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
	}


}
