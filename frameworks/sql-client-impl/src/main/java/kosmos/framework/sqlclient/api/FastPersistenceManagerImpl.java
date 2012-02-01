/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import kosmos.framework.bean.Pair;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmParameter;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.utility.ObjectUtils;

/**
 * Persists the entity.
 * This class is an alternative for the EntityManager.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FastPersistenceManagerImpl implements PersistenceManager{

	private InternalOrmQuery internaOrmlQuery;
	
	/**
	 * @param internaOrmlQuery the internaOrmlQuery to set
	 */
	public void setInternalOrmQuery(InternalOrmQuery internaOrmlQuery){
		this.internaOrmlQuery = internaOrmlQuery;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.util.List, kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	public int[] batchInsert(List<?> entity, PersistenceHints hints) {
		
		@SuppressWarnings("unchecked")
		OrmUpdateParameter<Object> context = new OrmUpdateParameter<Object>((Class<Object>)entity.get(0).getClass());	
		for(Object e : entity){
			setInsertData(context,e,hints);
			context.addBatch();
		}
		setHint(context,hints);
		return internaOrmlQuery.batchInsert(context);
	}


	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#persist(java.lang.Object, java.util.Map)
	 */
	@Override
	public int insert(Object entity, PersistenceHints hints) {
		
		@SuppressWarnings("unchecked")
		OrmUpdateParameter<Object> context = new OrmUpdateParameter<Object>((Class<Object>)entity.getClass());		
		setInsertData(context,entity,hints);
		setHint(context,hints);
		return internaOrmlQuery.insert(context);
	}
	
	/**
	 * Sets the attributes and keys.
	 * @param context the parameter
	 * @param entity the entity 
	 * @param hints the hints
	 */
	private void setInsertData(	OrmUpdateParameter<Object> context , Object entity, PersistenceHints hints){
		context.getCurrentValues().putAll(FastEntity.class.cast(entity).toPrimaryKeys());	
		for(Map.Entry<String, Object> e : FastEntity.class.cast(entity).toAttributes().entrySet()){
			if(ObjectUtils.isNotEmpty(e.getValue())){
				context.set(e.getKey(), e.getValue());
			}else{
				//NUll項目であるが更新対象とするもの
				String[] includables = hints.getIncludables();
				if(includables != null && Arrays.asList(includables).contains(e.getKey())){
					context.set(e.getKey(), e.getValue());
				}
			}
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#update(java.lang.Object, kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int update(Object entity,PersistenceHints hints){
		OrmUpdateParameter<?> condition = new OrmUpdateParameter(entity.getClass());
		setHint(condition,hints);
		
		Object found = hints.getFoundEntity();
		int result = 0;
		if(found != null){
			result = updateCompareFound(entity, found, condition);
		}else{
			result = updateEntity(entity,condition,hints);			
		}
		if(result == 0){
			throw new OptimisticLockException(entity);
		}
		if(result > 1){
			throw new PersistenceException("update count must be less than 1");
		}
		return result;
	}
	
	/**
	 * Updates the entity.
	 * 
	 * @param entity
	 * @param condition
	 * @throws OptimisticLockException throw when the updated count is 0
	 * @return updated count
	 */
	private int updateEntity(Object entity,OrmUpdateParameter<?> condition,PersistenceHints hints){
		
		FastEntity dst = FastEntity.class.cast(entity);
		Pair<String> dstVersion = dst.toVersioningValue();
		
		//更新条件設定、主キー+バージョン番号
		Map<String,Object> pks = dst.toPrimaryKeys();
		pks.put(dstVersion.getKey(), dstVersion.getValue());		
		condition.getConditions().addAll(createWhere(pks));
				
		//更新値
		Map<String,Object> attrs = dst.toAttributes();
		for(Map.Entry<String, Object> attr : attrs.entrySet()){
			if(ObjectUtils.isNotEmpty(attr.getValue())){
				condition.set(attr.getKey(), attr.getValue());
			}else{
				//NUll項目であるが更新対象とするもの
				String[] includables = hints.getIncludables();
				if(includables != null && Arrays.asList(includables).contains(attr.getKey())){
					condition.set(attr.getKey(), attr.getValue());
				}
			}
		}
		//楽観ロック番号
		condition.set(dstVersion.getKey(), new FixString(dstVersion.getKey() + " + 1"));
		
		return internaOrmlQuery.update(condition);		
	}
	
	/**
	 * Compare the found entity.
	 * 
	 * setting target is the column which is not equal to found entity.
	 * 
	 * @param entity
	 * @param found
	 * @param condition
	 * @return
	 */
	private int updateCompareFound(Object entity,Object found,OrmUpdateParameter<?> condition){
		FastEntity src = FastEntity.class.cast(found);
		FastEntity dst = FastEntity.class.cast(entity);
		Pair<String> dstVersion = dst.toVersioningValue();
		Pair<String> srcVersion = src.toVersioningValue();
		
		//バージョン番号違い
		if(!ObjectUtils.equals(dstVersion.getValue(),srcVersion.getValue())){
			return 0;
		}
		
		//主キー違い
		Map<String,Object> srcpks = src.toPrimaryKeys();
		Map<String,Object> dstpks = dst.toPrimaryKeys();
		int count = 0;
		for(Map.Entry<String, Object> e: dstpks.entrySet()){
			if(!ObjectUtils.equals(srcpks.get(e.getKey()),e.getValue())){
				throw new IllegalArgumentException("primary key must not be change : src = " + src + " dst = " + dst);
			}else{
				condition.getConditions().add(new WhereCondition(e.getKey(), count++, WhereOperand.Equal, e.getValue()));
			}
		}
		//異なる属性値
		Map<String,Object> srcattrs = src.toAttributes();
		Map<String,Object> dstattrs = dst.toAttributes();
		for(Map.Entry<String, Object> e: dstattrs.entrySet()){
			if(!ObjectUtils.equals(srcattrs.get(e.getKey()),e.getValue())){
				condition.set(e.getKey(), e.getValue());
			}
		}
		
		//ロック連番の上書き
		condition.set(dstVersion.getKey(), new FixString(dstVersion.getKey() + " + 1"));
		
		return internaOrmlQuery.update(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#delete(java.lang.Object, java.util.Map)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int delete(Object entity, PersistenceHints hints) {
		
		OrmUpdateParameter<?> condition = new OrmUpdateParameter(entity.getClass());
		setHint(condition,hints);
		
		int count = 0;
		for(Map.Entry<String, Object> e: FastEntity.class.cast(entity).toPrimaryKeys().entrySet()){
			condition.getConditions().add(new WhereCondition(e.getKey(), count++, WhereOperand.Equal, e.getValue()));
		}			
		//楽観ロック番号
		Pair<String> version = FastEntity.class.cast(entity).toVersioningValue();
		condition.getConditions().add(new WhereCondition(version.getKey(), count++, WhereOperand.Equal, version.getValue()));
		
		int result = internaOrmlQuery.delete(condition);
		if(result == 0){
			throw new OptimisticLockException(entity);
		}
		if(result > 1){
			throw new PersistenceException("update count must be less than 1");
		}
		return result;
	}
	

	/**
	 * Creates the condition;
	 * @param keys the keys 
	 * @return the condition
	 */
	private List<WhereCondition> createWhere(Map<String,Object> keys){
		List<WhereCondition> c = new ArrayList<WhereCondition>();
		int i = 0;
		for(Map.Entry<String, Object> e : keys.entrySet() ){
			if( !ObjectUtils.isNotEmpty(e.getValue()) )throw new IllegalArgumentException("primary key must not be empty");
			c.add(new WhereCondition(e.getKey(), i++, WhereOperand.Equal, e.getValue()));
		}
		return c;
	}
	
	/**
	 * Sets the hint to condition.
	 * @param condition condition
	 * @param hints hint
	 */
	private void setHint(OrmParameter<?> condition , PersistenceHints hints){
		for(Map.Entry<String, Object> h : hints.entrySet()){
			condition.setHint(h.getKey(),h.getValue());
		}
	}

}
