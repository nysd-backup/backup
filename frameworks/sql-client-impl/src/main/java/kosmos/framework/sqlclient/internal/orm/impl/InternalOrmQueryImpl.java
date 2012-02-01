/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NonUniqueResultException;

import kosmos.framework.sqlclient.api.FastEntity;
import kosmos.framework.sqlclient.api.free.FreeParameter;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.free.QueryCallback;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmParameter;
import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder.Bindable;
import kosmos.framework.utility.ClassUtils;
import kosmos.framework.utility.ReflectionUtils;
import kosmos.framework.utility.StringUtils;

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
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#find(java.lang.Class, java.util.Map, java.lang.Object[])
	 */
	@Override
	public <E> E find(OrmQueryParameter<E> context,Object... pks) {
		
		OrmQueryParameter<E> newContext = new OrmQueryParameter<E>(context.getEntityClass());

		//高速エンティティ
		if(FastEntity.class.isAssignableFrom(context.getEntityClass())){
			FastEntity entity = (FastEntity)ClassUtils.newInstance(context.getEntityClass());
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
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#getResultList(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	public <E> List<E> getResultList(OrmQueryParameter<E> condition) {
		FreeQueryParameter parameter = createParameter(condition);
		return internalQuery.getResultList(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#insert(java.lang.Object)
	 */
	@Override
	public int insert(OrmUpdateParameter<?> context) {
		
		String sql = sb.createInsert(context.getEntityClass(),context.getCurrentValues());
		
		FreeUpdateParameter parameter = new FreeUpdateParameter(true, context.getEntityClass().getName()+".insert", sql);

		//set statement
		setUpdateValule(parameter,context.getCurrentValues());
		
		setHint(context.getHints(),parameter);
		return internalQuery.executeUpdate(parameter);
	}
	

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#batchInsert(kosmos.framework.sqlclient.api.orm.OrmUpdateParameter)
	 */
	@Override
	public int[] batchInsert(OrmUpdateParameter<?> condition) {
		String sql = sb.createInsert(condition.getEntityClass(),condition.getBatchValues().get(0));
		FreeUpdateParameter parameter = new FreeUpdateParameter(true, condition.getEntityClass().getName()+".insert", sql);

		//更新値設定
		for(Map<String,Object> v: condition.getBatchValues()){
			//set statement
			setUpdateValule(parameter,v);
			parameter.addBatch();
		}
		setHint(parameter.getHints(),parameter);
		return internalQuery.batchUpdate(parameter);
	}


	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#update(kosmos.framework.sqlclient.api.orm.OrmQueryParameter, java.util.Map)
	 */
	@Override
	public int update(OrmUpdateParameter<?> condition) {
		
		String sql = sb.createUpdate(condition.getEntityClass(),condition.getFilterString(),condition.getConditions(), condition.getCurrentValues());		
		final FreeUpdateParameter parameter = new FreeUpdateParameter(true, condition.getEntityClass().getName()+".update", sql);		
		setCondition(condition,parameter);
		
		//set statement
		setUpdateValule(parameter,condition.getCurrentValues());
		
		setHint(condition.getHints(),parameter);
		
		return internalQuery.executeUpdate(parameter);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#batchUpdate(kosmos.framework.sqlclient.api.orm.OrmUpdateParameter)
	 */
	@Override
	public int[] batchUpdate(OrmUpdateParameter<?> condition) {
		String sql = sb.createUpdate(condition.getEntityClass(),condition.getFilterString(),condition.getBatchCondition().get(0), condition.getBatchValues().get(0));		
		final FreeUpdateParameter parameter = new FreeUpdateParameter(true, condition.getEntityClass().getName()+".batchUpdate", sql);
	
		for(int i = 0 ; i < condition.getBatchCondition().size(); i++){
			List<WhereCondition> c = condition.getBatchCondition().get(i);
			Map<String,Object> set = condition.getBatchValues().get(i);
			
			sb.setConditionParameters(condition.getFilterString(),condition.getEasyParams(),c, new Bindable(){
				public void setParameter(String key , Object value){
					parameter.getParam().put(key, value);
				}
			});
			setUpdateValule(parameter,set);
			
			parameter.addBatch();
			
		}
		setHint(condition.getHints(),parameter);
		return internalQuery.batchUpdate(parameter);
	}


	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#delete(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	public int delete(OrmUpdateParameter<?> condition) {
		String sql = sb.createDelete(condition.getEntityClass(),condition.getFilterString(),condition.getConditions());
		FreeUpdateParameter parameter = new FreeUpdateParameter(true, condition.getEntityClass().getName()+".delete", sql);
		setCondition(condition,parameter);
		setHint(condition.getHints(),parameter);
		return internalQuery.executeUpdate(parameter);
	}
	
	/**
	 * 更新値設定
	 * @param parameter
	 * @param bind
	 */
	private void setUpdateValule(FreeUpdateParameter parameter, Map<String,Object> bind){
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
	private void setHint(Map<String,Object> hints,FreeParameter parameter){
		for(Map.Entry<String, Object> e: hints.entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
	}
	
	/**
	 * Sets the parameter.
	 * @param condition
	 * @param parameter
	 */
	private void setCondition(OrmParameter<?> condition , final FreeParameter parameter){
		sb.setConditionParameters(condition.getFilterString(),condition.getEasyParams(),condition.getConditions(), new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#getFetchResult(kosmos.framework.sqlclient.api.orm.OrmQueryParameter, kosmos.framework.sqlclient.api.free.QueryCallback)
	 */
	@Override
	public <E> List<E> getFetchResult(OrmQueryParameter<E> condition,QueryCallback<E> callback) {
		FreeQueryParameter parameter = createParameter(condition);
		return internalQuery.getFetchResult(parameter);
	}
	
	/**
	 * Creates the selecting parameter
	 * @param condition the condition
	 * @return the parameter
	 */
	private <E> FreeQueryParameter createParameter(OrmQueryParameter<E> condition){
		String sql = sb.createSelect(condition);
		final FreeQueryParameter parameter = new FreeQueryParameter(condition.getEntityClass(), true, condition.getEntityClass().getName()+".select", sql);
	
		sb.setConditionParameters(condition.getFilterString(),condition.getEasyParams(),condition.getConditions(),new Bindable(){
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
