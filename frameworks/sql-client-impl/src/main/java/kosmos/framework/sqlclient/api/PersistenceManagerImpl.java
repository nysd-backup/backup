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
import javax.persistence.Version;

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
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.lang.Object[])
	 */
	@Override
	public int[] insert(Object[] entity) {
		return insert(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#insert(java.lang.Object[], kosmos.framework.sqlclient.api.PersistenceHints)
	 */
	@Override
	public int[] insert(Object[] entity, PersistenceHints hints) {
		
		@SuppressWarnings("unchecked")
		OrmUpdateParameter<Object> context = new OrmUpdateParameter<Object>((Class<Object>)entity[0].getClass());
		
		//エンティティから登録対象項目追加
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity[0].getClass(), Column.class);
		for(Object e : entity){
			setInsertValue(fs,e,context);
			context.addBatch();
		}
		
		//ヒント句設定
		for(Map.Entry<String, Object> e : hints.entrySet()){
			context.setHint(e.getKey(),e.getValue());
		}
		
		return internaOrmlQuery.batchInsert(context);
	}


	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#persist(java.lang.Object, java.util.Map)
	 */
	@Override
	public int insert(Object entity, PersistenceHints hints) {
		
		@SuppressWarnings("unchecked")
		OrmUpdateParameter<Object> context = new OrmUpdateParameter<Object>((Class<Object>)entity.getClass());
		
		//エンティティから登録対象項目追加
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Column.class);
		setInsertValue(fs,entity,context);
		
		//ヒント句設定
		for(Map.Entry<String, Object> e : hints.entrySet()){
			context.setHint(e.getKey(),e.getValue());
		}
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
			Column column = f.getAnnotation(Column.class);
			String name = column.name();
			if(StringUtils.isEmpty(name)){
				name = f.getName();
			}
			context.set(name, ReflectionUtils.get(f, entity));
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#merge(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int update(Object entity, Object findedEntity) {
		return update(entity,findedEntity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#merge(java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> int update(T entity, T findedEntity,PersistenceHints hints){
		OrmUpdateParameter<?> condition = new OrmUpdateParameter(entity.getClass());
		for(Map.Entry<String, Object> h : hints.entrySet()){
			condition.setHint(h.getKey(),h.getValue());
		}
		
		//比較対象エンティティと比較して結果が変更されていればset句への比較対象に含める
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Column.class);		
		List<Field> pk = new ArrayList<Field>();

		for(Field f : fs){
			Column column = f.getAnnotation(Column.class);
			Object src = ReflectionUtils.get(f, findedEntity);
			Object dst = ReflectionUtils.get(f, entity);
			
			//ロック連番
			if(f.getAnnotation(Version.class) != null){
				if( !ObjectUtils.equals(src, dst) ){
					//ロック連番の項目が異なっていたら並行性例外
					throw new OptimisticLockException(entity);
				}	
				dst = ((Number)dst).longValue() + 1;
				
			//主キー	
			}else if(f.getAnnotation(Id.class) != null){
				if( !ObjectUtils.equals(src, dst) ){
					throw new IllegalArgumentException("primary key must not be change : src = " + src + " dst = " + dst);
				}
				pk.add(f);
				continue;
			}
			
			//項目が異なる
			if( !ObjectUtils.equals(src, dst) ){
				String name = column.name();
				if(StringUtils.isEmpty(name)){
					name = f.getName();
				}
				condition.set(name, dst);
			}
		}
		
		//主キーを更新条件とする
		for(WhereCondition w : createPkWhere(pk.toArray(new Field[0]), entity)){
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
		for(Map.Entry<String, Object> h : hints.entrySet()){
			condition.setHint(h.getKey(),h.getValue());
		}
		
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Id.class);
		
		//主キーを更新条件とする
		for(WhereCondition w : createPkWhere(fs, entity)){
			condition.getConditions().add(w);
		}
		
		return internaOrmlQuery.delete(condition);
	}
	
	/**
	 * Creates the condition of primary key.
	 * @param fs the fields 
	 * @param entity the entity
	 * @return the condition
	 */
	private List<WhereCondition> createPkWhere(Field[] fs, Object entity){
		List<WhereCondition> pkWhere = new ArrayList<WhereCondition>();
		for(int i=0; i < fs.length;i++){
			Field f = fs[i];
			Column column = f.getAnnotation(Column.class);
			Object value = ReflectionUtils.get(f,entity);
			String name = column.name();
			if(StringUtils.isEmpty(name)){
				name = f.getName();
			}
			pkWhere.add(new WhereCondition(name,i, WhereOperand.Equal, value));
		}
		
		return pkWhere;
	}

}
