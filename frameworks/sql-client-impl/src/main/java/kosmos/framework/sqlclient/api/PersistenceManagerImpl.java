/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OptimisticLockException;
import javax.persistence.Version;

import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.utility.ReflectionUtils;

/**
 * persist the entity service.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceManagerImpl implements PersistenceManager{

	private InternalOrmQuery internalQuery;
	
	/**
	 * @param dao the dao to set
	 */
	public void setGenericDao(InternalOrmQuery internalQuery){
		this.internalQuery = internalQuery;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#persist(java.lang.Object)
	 */
	@Override
	public int insert(Object entity){
		return insert(entity,new PersistenceHints());
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#persist(java.lang.Object, java.util.Map)
	 */
	@Override
	public int insert(Object entity, PersistenceHints hints) {
		return internalQuery.insert(entity, hints);
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
		OrmContext<?> condition = new OrmContext(entity.getClass());
		for(Map.Entry<String, Object> h : hints.entrySet()){
			condition.setHint(h.getKey(),h.getValue());
		}
		
		//比較対象エンティティと比較して結果が変更されていればset句への比較対象に含める
		Field[] fs = ReflectionUtils.getAllAnotatedField(entity.getClass(), Column.class);
		Map<String,Object> set = new LinkedHashMap<String,Object>();
		for(Field f : fs){
			Column column = f.getAnnotation(Column.class);
			Object src = ReflectionUtils.get(f, findedEntity);
			Object dst = ReflectionUtils.get(f, entity);
			if(src == null && dst == null){
				continue;
			}else if( src != null && src.equals(dst)){
				continue;
			}
			//主キーの変更は認めない
			if(f.getAnnotation(Id.class) != null){
				throw new IllegalArgumentException("primary key must not be change : src = " + src + " dst = " + dst);
			}
			//ロック連番の項目が異なっていたら並行性例外
			if(f.getAnnotation(Version.class) != null){
				throw new OptimisticLockException(entity);
			}
			set.put(column.name(), ReflectionUtils.getField(f, entity));
		}
		
		//主キーを検索条件に設定する。
		
		return internalQuery.update(condition, set);
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
		
		OrmContext<?> condition = new OrmContext(entity.getClass());
		for(Map.Entry<String, Object> h : hints.entrySet()){
			condition.setHint(h.getKey(),h.getValue());
		}
		return internalQuery.delete(condition);
	}
	
	/**
	 * @param fs
	 * @param entity
	 * @return
	 */
	private List<WhereCondition> createPkWhere(Field[] fs, Object entity){
		
		return null;	//TODO
	}

}
