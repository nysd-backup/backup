/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Version;

import kosmos.framework.bean.Pair;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.OrmParameter;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.utility.ObjectUtils;
import kosmos.framework.utility.ReflectionUtils;
import kosmos.framework.utility.StringUtils;

/**
 * Persists the entity.
 * This class is an alternative for the EntityManager.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceManagerImpl implements PersistenceManager{

	private InternalOrmQuery internaOrmlQuery;
	
	/**
	 * @param internaOrmlQuery the internaOrmlQuery to set
	 */
	public void setInternalOrmQuery(InternalOrmQuery internaOrmlQuery){
		this.internaOrmlQuery = internaOrmlQuery;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#persist(java.lang.Object)
	 */
	@Override
	public int insert(Object entity){
		return insert(entity,new PersistenceHints());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.util.List)
	 */
	@Override
	public int[] insert(List<Object>  entity) {
		return insert(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.util.List, kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	public int[] insert(List<Object>  entity, PersistenceHints hints) {
		
		@SuppressWarnings("unchecked")
		OrmUpdateParameter<Object> context = new OrmUpdateParameter<Object>((Class<Object>)entity.get(0).getClass());
		
		//エンティティから登録対象項目追加
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.get(0).getClass(), Column.class);
		for(Object e : entity){
			setInsertValue(fs,e,context);
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
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Column.class);
		setInsertValue(fs,entity,context);			

		setHint(context,hints);
		
		return internaOrmlQuery.insert(context);
	}
	
	/**
	 * Sets the inserting value.
	 * @param fs the field
	 * @param entity the entity
	 * @param context the context
	 */
	private void setInsertValue(Field[] fs , Object entity, OrmUpdateParameter<Object> context){
		for(Field f : fs){			
			context.set(getColumnName(f), ReflectionUtils.get(f, entity));
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#merge(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int update(Object entity) {
		return update(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#merge(java.lang.Object, java.lang.Object, java.util.Map)
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
			result = updateEntity(entity,condition);			
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
	private int updateEntity(Object entity,OrmUpdateParameter<?> condition){
		
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Column.class);		
		List<Pair<Field>> where = new ArrayList<Pair<Field>>();
		for(Field f : fs){			
			Object dst = ReflectionUtils.get(f, entity);
					
			//ロック連番
			if(f.getAnnotation(Version.class) != null){						
				String name = getColumnName(f);
				condition.set(name,new FixString(name + " + 1"));
				where.add(new Pair<Field>(f,dst));
			//主キー	
			}else if(f.getAnnotation(Id.class) != null){				
				where.add(new Pair<Field>(f,dst));
				
			//その他(空項目はupdateしない)
			}else if(ObjectUtils.isNotEmpty(dst)){
				condition.set(getColumnName(f), dst);
			}		
		}		
		//主キーを更新条件とする
		for(WhereCondition w : createPkWhere(where)){
			condition.getConditions().add(w);
		}
		int result = internaOrmlQuery.update(condition);
		if(result == 0 ){
			throw new OptimisticLockException(entity);
		}
		return result;
	}
		

	
	/**
	 * Compare the found entity.
	 * 
	 * setting target is the column which is not equal to found entity.
	 * 
	 * @param entity
	 * @param findedEntity
	 * @param condition
	 * @return
	 */
	private int updateCompareFound(Object entity , Object found ,OrmUpdateParameter<?> condition){
		
		//比較対象エンティティと比較して結果が変更されていればset句への比較対象に含める
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Column.class);		
		List<Pair<Field>> where = new ArrayList<Pair<Field>>();
		
		for(Field f : fs){			
			Object src = ReflectionUtils.get(f, found);
			Object dst = ReflectionUtils.get(f, entity);
			
			//ロック連番
			if(f.getAnnotation(Version.class) != null){
				if( !ObjectUtils.equals(src, dst) ){
					//ロック連番の項目が異なっていたら並行性例外
					throw new OptimisticLockException(entity);
				}	
				String name = getColumnName(f);
				condition.set(name,new FixString(name + " + 1"));
				
			//主キー	
			}else if(f.getAnnotation(Id.class) != null){
				if( !ObjectUtils.equals(src, dst) ){
					throw new IllegalArgumentException("primary key must not be change : src = " + src + " dst = " + dst);
				}
				where.add(new Pair<Field>(f,dst));
				continue;
			//その他	
			}else if( !ObjectUtils.equals(src, dst) ){
				condition.set(getColumnName(f), dst);
			}
		}
			
		//主キーを更新条件とする
		for(WhereCondition w : createPkWhere(where)){
			condition.getConditions().add(w);
		}
		return internaOrmlQuery.update(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#delete(java.lang.Object)
	 */
	@Override
	public int delete(Object entity) {
		return delete(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#delete(java.lang.Object, java.util.Map)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int delete(Object entity, PersistenceHints hints) {
		
		OrmUpdateParameter<?> condition = new OrmUpdateParameter(entity.getClass());

		setHint(condition,hints);
		
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Id.class);		
		List<Pair<Field>> key = new ArrayList<Pair<Field>>(); 
		for(Field f : fs){
			key.add(new Pair<Field>(f,ReflectionUtils.get(f,entity)));
		}
		//主キーを更新条件とする
		for(WhereCondition w : createPkWhere(key)){
			condition.getConditions().add(w);
		}
		
		return internaOrmlQuery.delete(condition);
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
	
	/**
	 * Creates the condition of primary key.
	 * @param fs the fields 
	 * @param entity the entity
	 * @return the condition
	 */
	private List<WhereCondition> createPkWhere(List<Pair<Field>> where){
		List<WhereCondition> pkWhere = new ArrayList<WhereCondition>();
		for(int i=0; i < where.size();i++){
			Pair<Field> p = where.get(i);	
			if( !ObjectUtils.isNotEmpty(p.getValue()) ){
				throw new IllegalArgumentException("primary key must not be empty");
			}
			String name = getColumnName(p.getKey());
			pkWhere.add(new WhereCondition(name,i, WhereOperand.Equal, p.getValue()));
		}
		
		return pkWhere;
	}
	
	/**
	 * Gets the column name.
	 * @param f the field 
	 * @return the column name
	 */
	private String getColumnName(Field f){
		Column column = f.getAnnotation(Column.class);
		String name = column.name();
		if(StringUtils.isEmpty(name)){
			name = f.getName();
		}
		return name;
	}

}
