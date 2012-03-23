/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

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

import kosmos.framework.bean.Pair;
import kosmos.framework.sqlclient.api.free.BatchUpdateFactory;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.orm.FixString;
import kosmos.framework.sqlclient.api.orm.PersistenceHints;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder.Bindable;
import kosmos.framework.sqlclient.internal.orm.impl.SQLStatementBuilderImpl;
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

	/** the SQLStatementBuilder */
	private SQLStatementBuilder sb = new SQLStatementBuilderImpl();
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/** the context provider */
	private PersistenceContextProvider contextProvider;
	
	private BatchUpdateFactory batchUpdateFactory;
	
	/**
	 * @param batchUpdateFactory the batchUpdateFactory to set
	 */
	public void setBatchUpdateFactory(BatchUpdateFactory batchUpdateFactory) {
		this.batchUpdateFactory = batchUpdateFactory;
	}
	
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
	 * @param contextProvider the contextProvider to set
	 */
	public void setContextProvider(PersistenceContextProvider contextProvider) {
		this.contextProvider = contextProvider;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#persist(java.lang.Object, java.util.Map)
	 */
	@Override
	public void persist(Object entity, PersistenceHints hints) {
		
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
		
		PersistenceContext context = contextProvider.getContext();
		if(context.isEnabled()){
			context.add(entity, freeUpdateParameter);
		}else{
			internalQuery.executeUpdate(freeUpdateParameter);
		}
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#merge(java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	public <T> void merge(T entity,T found ,PersistenceHints hints){

		Map<String,Object> setValues = new LinkedHashMap<String, Object>();
		List<WhereCondition> conditions = null;
		if(entity instanceof FastEntity){
			FastEntity src = FastEntity.class.cast(found);
			FastEntity dst = FastEntity.class.cast(entity);
			Pair<String> dstVersion = dst.toVersioningValue();
			Pair<String> srcVersion = src.toVersioningValue();
			conditions = new ArrayList<WhereCondition>();
			
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
			
			//属性は全てset句
			setValues.putAll(dst.toAttributes());			
			
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
					setValues.put(getColumnName(m), dst);
				}
			}
			conditions = createPkWhere(where);		
		}
		String sql = sb.createUpdate(entity.getClass(), null,conditions , setValues);		
		FreeUpdateParameter parameter = new FreeUpdateParameter();
		parameter.setSql(sql);
		parameter.setQueryId(entity.getClass().getSimpleName()+".update");
		parameter.setParam(setValues);
		update(parameter,entity, hints, conditions);
	}
	
	/**
	 * @param parameter
	 * @param conditions
	 */
	private void setWhereCondition(final FreeUpdateParameter parameter,List<WhereCondition> conditions){
		sb.setConditionParameters(null,null,conditions, new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#delete(java.lang.Object, java.util.Map)
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
				Object dst = ReflectionUtils.invokeMethod(m, entity);					
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
		String sql = sb.createDelete(entity.getClass(), null, conditions);
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
		PersistenceContext context = contextProvider.getContext();
		if(context.isEnabled()){
			context.add(entity, parameter);
		}else{
			int result = internalQuery.executeUpdate(parameter);
			if(result == 0){
				throw new OptimisticLockException(entity);
			}
			if(result > 1){
				throw new PersistenceException("update count must be less than 1");
			}
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
	private String getColumnName(Method m){
		Column column = m.getAnnotation(Column.class);
		String name = column.name();
		if(StringUtils.isEmpty(name)){
			name = ReflectionUtils.getPropertyNameFromGetter(m);
		}
		return name;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.PersistenceManager#flush()
	 */
	@Override
	public void flush() {
		PersistenceContext context = contextProvider.getContext();
		if(context.isEnabled()){
			context.flush(batchUpdateFactory);
		}		
	}

}
