/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Version;

import kosmos.framework.sqlclient.PersistenceHints;
import kosmos.framework.sqlclient.ReflectionUtils;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.free.strategy.InternalQuery;
import kosmos.framework.sqlclient.orm.strategy.SQLStatementBuilder;
import kosmos.framework.sqlclient.orm.strategy.SQLStatementBuilder.Bindable;
import kosmos.framework.sqlclient.orm.strategy.SQLStatementBuilderImpl;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Persists the entity.
 * This class is an alternative for the EntityManager.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PersistenceManagerImpl implements PersistenceManager{

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
	 * @see kosmos.framework.sqlclient.orm.PersistenceManager#persist(java.lang.Object, java.util.Map)
	 */
	@Override
	public void persist(Object entity, PersistenceHints hints) {
		internalQuery.executeUpdate(createInsertingParameter(entity,hints));
	}
	
	/**
	 * Creates the inserting parameter
	 * @param entity the entity
	 * @param hints the hints
	 * @return
	 */
	private FreeUpdateParameter createInsertingParameter(Object entity, PersistenceHints hints) {
		Map<String,Object> bindValues = new LinkedHashMap<String,Object>();
		if(entity instanceof FastEntity){
			FastEntity e = FastEntity.class.cast(entity);
			bindValues.putAll(e.toPrimaryKeys());
			bindValues.putAll(e.toAttributes());
		}else{
			List<Method> ms = ReflectionUtils.getAnotatedGetter(entity.getClass(), Column.class);			
			for(Method m : ms){						
				Object value = ReflectionUtils.invokeMethod(m, entity);
				bindValues.put(getColumnName(m),value);		
			}
		}
		String sql = sb.createInsert(entity.getClass(), bindValues);
		FreeUpdateParameter freeUpdateParameter = new FreeUpdateParameter();
		freeUpdateParameter.setSql(sql);
		freeUpdateParameter.setQueryId(entity.getClass().getSimpleName()+".insert");
		freeUpdateParameter.setHints(hints);
		freeUpdateParameter.setParam(bindValues);
		return freeUpdateParameter;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.orm.PersistenceManager#merge(java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public <T> void merge(T entity,T found ,PersistenceHints hints){
		if(found == null){
			throw new IllegalArgumentException();
		}
		List<WhereCondition> conditions = new ArrayList<WhereCondition>();
		update(createUpdatingParameter(entity,found,hints,conditions),entity, hints,conditions);
	}
	
	/**
	 * Creates the updating parameter.
	 * @param entity the entity 
	 * @param found the found entity
	 * @param hints the hints
	 * @param conditions the where conditions
	 * @return the parameter
	 */
	private <T> FreeUpdateParameter createUpdatingParameter(T entity,T found ,PersistenceHints hints,List<WhereCondition> conditions){
		Map<String,Object> setValues = new LinkedHashMap<String, Object>();
		if(found != null){
			if(entity instanceof FastEntity){
				FastEntity src = FastEntity.class.cast(found);
				FastEntity dst = FastEntity.class.cast(entity);
				Pair<String> dstVersion = dst.toVersioningValue();
				Pair<String> srcVersion = src.toVersioningValue();			
				
				//バージョン番号違い
				if(!ObjectUtils.equals(dstVersion.getValue(),srcVersion.getValue())){
					throw new OptimisticLockException(entity);
				}
				
				//主キー違い
				Map<String,Object> srcpks = src.toPrimaryKeys();
				Map<String,Object> dstpks = dst.toPrimaryKeys();
				int count = 0;
				for(Map.Entry<String, Object> e: dstpks.entrySet()){
					if(!ObjectUtils.equals(srcpks.get(e.getKey()),e.getValue())){
						throw new IllegalArgumentException("primary key must not be change : src = " + src + " dst = " + dst);
					}else{
						conditions.add(new WhereCondition(e.getKey(), count++, WhereOperand.Equal, e.getValue()));
					}
				}
				//検索値と異なる値のみset句
				Map<String,Object> srcAttr = src.toAttributes();
				Map<String,Object> dstAttr = dst.toAttributes();
				for(Map.Entry<String, Object> e: dstAttr.entrySet()){
					Object srcValue = srcAttr.get(e.getKey());
					if(!ObjectUtils.equals(srcValue, e.getValue())){
						setValues.put(e.getKey(), e.getValue());
					}
				}
				
				//ロック連番の上書き
				setValues.put(dstVersion.getKey(), new FixString(dstVersion.getKey() + " + 1"));
				
			}else{
				List<Method> ms = ReflectionUtils.getAnotatedGetter(entity.getClass(), Column.class);		
				List<Pair<Method>> where = new ArrayList<Pair<Method>>();	
				for(Method m : ms){			
					Object src = ReflectionUtils.invokeMethod(m, found);
					Object dst = ReflectionUtils.invokeMethod(m, entity);
					
					//ロック連番
					if(m.getAnnotation(Version.class) != null){
						if( !ObjectUtils.equals(src, dst) ){
							throw new OptimisticLockException(entity);
						}	
						String name = getColumnName(m);
						setValues.put(name,new FixString(name + " + 1"));				
					//主キー	
					}else if(m.getAnnotation(Id.class) != null){
						if( !ObjectUtils.equals(src, dst) ){
							throw new IllegalArgumentException("primary key must not be changed : src = " + src + " dst = " + dst);
						}
						where.add(new Pair<Method>(m,dst));
					//その他	
					}else{
						if( !ObjectUtils.equals(src, dst) ){
							setValues.put(getColumnName(m), dst);
						}						
					}
				}
				conditions.addAll(createPkWhere(where));		
			}
		}else {
			if(entity instanceof FastEntity){
				FastEntity dst = FastEntity.class.cast(entity);
				Map<String,Object> dstpks = dst.toPrimaryKeys();
				int count  = 0;
				for(Map.Entry<String, Object> e: dstpks.entrySet()){
					conditions.add(new WhereCondition(e.getKey(), count++, WhereOperand.Equal, e.getValue()));
				}
				//全項目set句に含める
				setValues.putAll(dst.toAttributes());						
				Pair<String> dstVersion = dst.toVersioningValue();
				setValues.put(dstVersion.getKey(), new FixString(dstVersion.getKey() + " + 1"));
			}else{
				List<Method> ms = ReflectionUtils.getAnotatedGetter(entity.getClass(), Column.class);		
				List<Pair<Method>> where = new ArrayList<Pair<Method>>();	
				for(Method m : ms){								
					Object dst = ReflectionUtils.invokeMethod(m, entity);
					
					//ロック連番
					if(m.getAnnotation(Version.class) != null){
						String name = getColumnName(m);
						setValues.put(name,new FixString(name + " + 1"));				
					//主キー	
					}else if(m.getAnnotation(Id.class) != null){						
						where.add(new Pair<Method>(m,dst));
					//その他	
					}else{
						setValues.put(getColumnName(m), dst);
					}
				}
				conditions.addAll(createPkWhere(where));		
			}
		}
		String sql = sb.createUpdate(entity.getClass(), conditions , setValues);		
		FreeUpdateParameter parameter = new FreeUpdateParameter();
		parameter.setSql(sql);
		parameter.setQueryId(entity.getClass().getSimpleName()+".update");
		parameter.setParam(setValues);
		return parameter;
	}
	
	/**
	 * @param parameter
	 * @param conditions
	 */
	private void setWhereCondition(final FreeUpdateParameter parameter,List<WhereCondition> conditions){
		sb.setConditionParameters(conditions, new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
	}

	/**
	 * @see kosmos.framework.sqlclient.orm.PersistenceManager#delete(java.lang.Object, java.util.Map)
	 */
	@Override
	public void remove(Object entity, PersistenceHints hints) {
			
		List<WhereCondition> conditions = null;
		if(entity instanceof FastEntity){
			conditions = new ArrayList<WhereCondition>();
			int count = 0;
			for(Map.Entry<String, Object> e: FastEntity.class.cast(entity).toPrimaryKeys().entrySet()){
				conditions.add(new WhereCondition(e.getKey(), count++, WhereOperand.Equal, e.getValue()));
			}			
			//楽観ロック番号
			Pair<String> version = FastEntity.class.cast(entity).toVersioningValue();
			conditions.add(new WhereCondition(version.getKey(), count++, WhereOperand.Equal, version.getValue()));
			
		}else{
			List<Method> ms = ReflectionUtils.getAnotatedGetter(entity.getClass(), Column.class);		
			List<Pair<Method>> where = new ArrayList<Pair<Method>>();
			for(Method m : ms){			
				Object dst = ReflectionUtils.invokeMethod(m,entity);				
				//ロック連番
				if(m.getAnnotation(Version.class) != null){						
					String name = getColumnName(m);			
					if(dst == null){
						throw new IllegalArgumentException(name + " must be not null");
					}
					where.add(new Pair<Method>(m,dst));
				//主キー	
				}else if(m.getAnnotation(Id.class) != null){				
					where.add(new Pair<Method>(m,dst));			
				}
			}		
			conditions = createPkWhere(where);
		}
		String sql = sb.createDelete(entity.getClass(), conditions);
		FreeUpdateParameter parameter = new FreeUpdateParameter();
		parameter.setSql(sql);
		parameter.setQueryId(entity.getClass().getSimpleName()+".delete");
		update(parameter,entity, hints, conditions) ;
	}
	
	/**
	 * @param entity
	 * @param hints
	 * @param conditions
	 * @param sql
	 * @param sqlId
	 */
	private void update(FreeUpdateParameter parameter, Object entity, PersistenceHints hints,List<WhereCondition> conditions){		
		setWhereCondition(parameter, conditions);
		parameter.setHints(hints);
		int result = internalQuery.executeUpdate(parameter);
		if(result == 0){
			throw new OptimisticLockException(entity);
		}
		if(result > 1){
			throw new PersistenceException("update count must be less than 1");
		}
	}
	
	/**
	 * Creates the condition of primary key.
	 * @param fs the fields 
	 * @param entity the entity
	 * @return the condition
	 */
	private List<WhereCondition> createPkWhere(List<Pair<Method>> where){
		List<WhereCondition> pkWhere = new ArrayList<WhereCondition>();
		for(int i=0; i < where.size();i++){
			Pair<Method> p = where.get(i);	
			if( p.getValue() == null || (p.getValue() instanceof String && ((String)p.getValue()).isEmpty())){
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
	private String getColumnName(Method m){
		Column column = m.getAnnotation(Column.class);
		String name = column.name();
		if(StringUtils.isEmpty(name)){
			name = ReflectionUtils.getPropertyNameFromGetter(m);
		}
		return name;
	}

	/**
	 * @see kosmos.framework.sqlclient.orm.PersistenceManager#persist(java.util.List, kosmos.framework.sqlclient.PersistenceHints)
	 */
	@Override
	public <T> void batchPersist(List<T> entity, PersistenceHints hints) {
		List<FreeUpdateParameter> params = new ArrayList<FreeUpdateParameter>();
		for(Object e : entity){
			params.add(createInsertingParameter(e, hints));
		}
		internalQuery.executeBatch(params);
	}

	/**
	 * @see kosmos.framework.sqlclient.orm.PersistenceManager#merge(java.util.List, kosmos.framework.sqlclient.PersistenceHints)
	 */
	@Override
	public <T> void batchUpdate(List<T> entity, PersistenceHints hints) {
		
		List<FreeUpdateParameter> params = new ArrayList<FreeUpdateParameter>();
		for(Object e : entity){
			List<WhereCondition> conditions = new ArrayList<WhereCondition>();
			FreeUpdateParameter parameter = createUpdatingParameter(e,null,hints,conditions);
			setWhereCondition(parameter, conditions);
			parameter.setHints(hints);
			params.add(parameter);
		}
		internalQuery.executeBatch(params);		
	}

}
