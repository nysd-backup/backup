/**
 * Copyright 2011 the original author
 */
package client.sql.orm.strategy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NonUniqueResultException;


import org.apache.commons.lang.StringUtils;

import client.sql.ReflectionUtils;
import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeSelectParameter;
import client.sql.free.FreeUpsertParameter;
import client.sql.free.strategy.InternalQuery;
import client.sql.orm.FastEntity;
import client.sql.orm.FixString;
import client.sql.orm.OrmQueryParameter;
import client.sql.orm.OrmSelectParameter;
import client.sql.orm.OrmUpdateParameter;
import client.sql.orm.WhereCondition;
import client.sql.orm.WhereOperand;
import client.sql.orm.strategy.InternalOrmQuery;
import client.sql.orm.strategy.SQLStatementBuilder;
import client.sql.orm.strategy.SQLStatementBuilder.Bindable;


/**
 * The internal ORM query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InternalOrmQueryImpl implements InternalOrmQuery{
	
	/** the SQLStatementBuilder */
	private SQLStatementBuilder sb = new SQLStatementBuilderImpl();
	
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
		
		OrmSelectParameter<E> newContext = new OrmSelectParameter<E>(context.getEntityClass());

		//高速エンティティ
		if(FastEntity.class.isAssignableFrom(context.getEntityClass())){
			
			FastEntity entity = (FastEntity)ReflectionUtils.newInstance(context.getEntityClass());
			int i = 0;
			for(String e: entity.toPrimaryKeys().keySet()){
				newContext.getConditions().add(new WhereCondition(e,i,WhereOperand.Equal,pks[i]));
				i++;
			}
		//通常エンティティ	
		}else{
			List<Method> ms = ReflectionUtils.getAnotatedGetter(context.getEntityClass(), Id.class);
			if(ms.size() != pks.length){
				throw new IllegalArgumentException("invalid primary key count");
			}		
			for(int i = 0 ; i < ms.size(); i++){
				Object pk = pks[i];
				Method f = ms.get(i);
				Column col = f.getAnnotation(Column.class);
				String name = col.name();
				if(StringUtils.isEmpty(name)){
					name = ReflectionUtils.getPropertyNameFromGetter(f);
				}
				newContext.getConditions().add(new WhereCondition(name,i,WhereOperand.Equal,pk));
			}
		}

		newContext.setFirstResult(context.getFirstResult());
		newContext.setMaxSize(2);
		newContext.setLockModeType(context.getLockModeType());
		for(Map.Entry<String, Object> h : context.getHints().entrySet()){
			newContext.setHint(h.getKey(), h.getValue());
		}		
		
		List<E> resultList = getResultList(newContext);
		if(resultList.isEmpty()){
			return null;
		}else if(resultList.size() > 1){
			throw new NonUniqueResultException("too many rows entity = " + context.getEntityClass() + " :pkvalue = " + pks );
		}
		return resultList.get(0);
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
		final FreeUpsertParameter parameter = new FreeUpsertParameter();
		parameter.setSql(sql);
		parameter.setQueryId(condition.getEntityClass().getName()+".update");
		setCondition(condition,parameter);
		
		//set statement
		setUpdateValule(parameter,condition.getCurrentValues());
		
		setHint(condition.getHints(),parameter);
		
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#delete(client.sql.orm.OrmSelectParameter)
	 */
	@Override
	public int delete(OrmUpdateParameter<?> condition) {
		String sql = sb.createDelete(condition.getEntityClass(),condition.getConditions());
		FreeUpsertParameter parameter =new FreeUpsertParameter();
		parameter.setSql(sql);
		parameter.setQueryId(condition.getEntityClass().getName()+".delete");
		setCondition(condition,parameter);
		setHint(condition.getHints(),parameter);
		return internalQuery.executeUpdate(parameter);
	}
	
	/**
	 * Sets the updating value.
	 * 
	 * @param parameter the parameter to
	 * @param bind the bind
	 */
	private void setUpdateValule(FreeUpsertParameter parameter, Map<String,Object> bind){
		for(Map.Entry<String, Object> v: bind.entrySet()){
			if(!(v.getValue() instanceof FixString)){
				parameter.getParam().put(v.getKey(),v.getValue());
			}
		}
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

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#getFetchResult(client.sql.orm.OrmSelectParameter, client.sql.free.QueryCallback)
	 */
	@Override
	public <E> List<E> getFetchResult(OrmSelectParameter<E> condition) {
		FreeSelectParameter parameter = createParameter(condition);
		return internalQuery.getFetchResult(parameter);
	}
	
	/**
	 * Creates the selecting parameter
	 * @param condition the condition
	 * @return the parameter
	 */
	private <E> FreeSelectParameter createParameter(OrmSelectParameter<E> condition){
		String sql = sb.createSelect(condition);
		final FreeSelectParameter parameter = new FreeSelectParameter();
		parameter.setSql(sql);
		parameter.setResultType(condition.getEntityClass());
		parameter.setQueryId(condition.getEntityClass().getName()+".select");
	
		sb.setConditionParameters(condition.getConditions(),new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});

		setHint(condition.getHints(),parameter);
		parameter.setFirstResult( condition.getFirstResult());			
		parameter.setMaxSize(condition.getMaxSize());
		return parameter;
	}


}
