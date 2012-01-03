/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.orm.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NonUniqueResultException;

import kosmos.framework.sqlclient.api.free.FreeParameter;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.orm.OrmParameter;
import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder.Bindable;
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
		
		Field[] fs = ReflectionUtils.getAllAnotatedField(context.getEntityClass(), Id.class);
		if(fs.length != pks.length){
			throw new IllegalArgumentException("invalid primary key count");
		}
		
		OrmQueryParameter<E> newContext = new OrmQueryParameter<E>(context.getEntityClass());
		newContext.setFirstResult(context.getFirstResult());
		newContext.setMaxSize(2);
		newContext.setLockModeType(context.getLockModeType());
		for(Map.Entry<String, Object> h : context.getHints().entrySet()){
			newContext.setHint(h.getKey(), h.getValue());
		}
		
		for(int i = 0 ; i < fs.length; i++){
			Object pk = pks[i];
			Field f = fs[i];
			Column col = f.getAnnotation(Column.class);
			String name = col.name();
			if(StringUtils.isEmpty(name)){
				name = f.getName();
			}
			newContext.getConditions().add(new WhereCondition(name,i,WhereOperand.Equal,pk));
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
		return internalQuery.getResultList(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#insert(java.lang.Object)
	 */
	@Override
	public int insert(OrmUpdateParameter<?> context) {
		
		String sql = sb.createInsert(context.getEntityClass(),context.getCurrentValues().keySet());
		
		FreeUpdateParameter parameter = new FreeUpdateParameter(true, context.getEntityClass().getName()+".insert", sql);

		//更新値設定
		for(Map.Entry<String, Object> e: context.getCurrentValues().entrySet()){
			parameter.getParam().put(e.getKey(), e.getValue());
		}
		
		setHint(context.getHints(),parameter);
		return internalQuery.executeUpdate(parameter);
	}
	

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#batchInsert(kosmos.framework.sqlclient.api.orm.OrmUpdateParameter)
	 */
	@Override
	public int[] batchInsert(OrmUpdateParameter<?> condition) {
		String sql = sb.createInsert(condition.getEntityClass(),condition.getBatchValues().get(0).keySet());
		FreeUpdateParameter parameter = new FreeUpdateParameter(true, condition.getEntityClass().getName()+".insert", sql);

		//更新値設定
		for(Map<String,Object> v: condition.getBatchValues()){
			for(Map.Entry<String, Object> e: v.entrySet()){
				parameter.getParam().put(e.getKey(), e.getValue());
			}
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
		
		String sql = sb.createUpdate(condition.getEntityClass(),condition.getFilterString(),condition.getConditions(), condition.getCurrentValues().keySet());		
		final FreeUpdateParameter parameter = new FreeUpdateParameter(true, condition.getEntityClass().getName()+".update", sql);		
		setCondition(condition,parameter);
		
		//set statement
		for(Map.Entry<String, Object> v: condition.getCurrentValues().entrySet()){
			parameter.getParam().put(v.getKey(),v.getValue());
		}
		
		setHint(condition.getHints(),parameter);
		
		return internalQuery.executeUpdate(parameter);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#batchUpdate(kosmos.framework.sqlclient.api.orm.OrmUpdateParameter)
	 */
	@Override
	public int[] batchUpdate(OrmUpdateParameter<?> condition) {
		String sql = sb.createUpdate(condition.getEntityClass(),condition.getFilterString(),condition.getBatchCondition().get(0), condition.getBatchValues().get(0).keySet());		
		final FreeUpdateParameter parameter = new FreeUpdateParameter(true, condition.getEntityClass().getName()+".batchUpdate", sql);
	
		for(int i = 0 ; i < condition.getBatchCondition().size(); i++){
			List<WhereCondition> c = condition.getBatchCondition().get(i);
			Map<String,Object> set = condition.getBatchValues().get(i);
			
			sb.setConditionParameters(condition.getFilterString(),condition.getEasyParams(),c, new Bindable(){
				public void setParameter(String key , Object value){
					parameter.getParam().put(key, value);
				}
			});
			for(Map.Entry<String, Object> v: set.entrySet()){
				parameter.getParam().put(v.getKey(),v.getValue());
			}
			
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

}
