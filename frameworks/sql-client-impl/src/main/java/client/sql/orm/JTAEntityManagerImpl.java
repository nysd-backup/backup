/**
 * Copyright 2011 the original author
 */
package client.sql.orm;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import client.sql.EngineHints;
import client.sql.PersistenceHints;
import client.sql.ReflectionUtils;
import client.sql.free.FreeSelectParameter;
import client.sql.free.FreeUpsertParameter;
import client.sql.free.strategy.InternalQuery;
import client.sql.orm.strategy.SQLStatementBuilder;
import client.sql.orm.strategy.SQLStatementBuilder.Bindable;
import client.sql.orm.strategy.SQLStatementBuilderImpl;

/**
 * A entity manager only for 'SQLEngine'.
 * 
 * <pre>
 * Only use this when the JTADataSourceTransactionManager is active.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class JTAEntityManagerImpl implements EntityManager{
	
	/** the SQLStatementBuilder */
	private SQLStatementBuilder sb = new SQLStatementBuilderImpl();
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/** query hints */
	private EngineHints hints = new EngineHints();
	
	/** the dataSource */
	private DataSource dataSource;
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * @return the dataSource
	 */
	protected DataSource getDataSoure(){
		return dataSource;
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
	 * Batch update.
	 * 
	 * @param entities the entities
	 */
	public void batchPersist(List<?> entities){
		List<FreeUpsertParameter> params = new ArrayList<FreeUpsertParameter>();
		for(Object e : entities){
			params.add(createInsertingParameter(e));
		}
		internalQuery.executeBatch(params);
	}

	/**
	 * @see javax.persistence.EntityManager#persist(java.lang.Object)
	 */
	@Override
	public void persist(Object entity) {		
		internalQuery.executeUpdate(createInsertingParameter(entity));
	}
	
	/**
	 * Creates the inserting parameter.
	 * @param entity the entity
	 * @return parameter
	 */
	private FreeUpsertParameter createInsertingParameter(Object entity) {
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
		FreeUpsertParameter freeUpdateParameter = new FreeUpsertParameter();	
		freeUpdateParameter.setEntityManager(this);
		freeUpdateParameter.setSql(sql);
		freeUpdateParameter.setQueryId(entity.getClass().getSimpleName()+".insert");
		freeUpdateParameter.setHints(getProperties());
		freeUpdateParameter.setParam(bindValues);
		return freeUpdateParameter;
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
	 * @see javax.persistence.EntityManager#merge(java.lang.Object)
	 */
	@Override
	public <T> T merge(T entity) {
		return merge(entity,null);
	}
	
	/**
	 * Updates
	 * @param entity the entity
	 * @param found the found entity
	 * @return the entity to update.
	 */
	public <T> T merge(T entity, T found) {
		List<WhereCondition> conditions = new ArrayList<WhereCondition>();
		update(createUpdatingParameter(entity,found,hints,conditions),entity, hints,conditions);
		return entity;
	}
	
	/**
	 * Batch update.
	 * @param entities the entities
	 */
	public void batchMerge(List<?> entities) {
		List<FreeUpsertParameter> params = new ArrayList<FreeUpsertParameter>();
		for(Object e : entities){
			List<WhereCondition> conditions = new ArrayList<WhereCondition>();
			FreeUpsertParameter parameter = createUpdatingParameter(e,null,hints,conditions);
			setWhereCondition(parameter, conditions);
			parameter.setHints(hints);
			parameter.setEntityManager(this);
			params.add(parameter);	
		}
		internalQuery.executeBatch(params);
	}
	
	
	/**
	 * Creates the updating parameter.
	 * @param entity the entity 
	 * @param found the found entity
	 * @param hints the hints
	 * @param conditions the where conditions
	 * @return the parameter
	 */
	private <T> FreeUpsertParameter createUpdatingParameter(T entity,T found ,PersistenceHints hints,List<WhereCondition> conditions){
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
		FreeUpsertParameter parameter = new FreeUpsertParameter();
		parameter.setSql(sql);
		parameter.setQueryId(entity.getClass().getSimpleName()+".update");
		parameter.setParam(setValues);
		return parameter;
	}

	/**
	 * @see javax.persistence.EntityManager#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object entity) {
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
		FreeUpsertParameter parameter = new FreeUpsertParameter();
		parameter.setSql(sql);
		parameter.setQueryId(entity.getClass().getSimpleName()+".delete");
		update(parameter,entity, hints, conditions) ;
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
	 * @param entity
	 * @param hints
	 * @param conditions
	 * @param sql
	 * @param sqlId
	 */
	private void update(FreeUpsertParameter parameter, Object entity,PersistenceHints hints,List<WhereCondition> conditions){		
		setWhereCondition(parameter, conditions);
		parameter.setEntityManager(this);
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
	 * @param parameter
	 * @param conditions
	 */
	private void setWhereCondition(final FreeUpsertParameter parameter,List<WhereCondition> conditions){
		sb.setConditionParameters(conditions, new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
	}

	/**
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return find(entityClass,primaryKey,null,hints);
	}

	/**
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, java.util.Map)
	 */
	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			Map<String, Object> properties) {
		return find(entityClass,primaryKey,null,properties);
	}

	/**
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			LockModeType lockMode) {
		return find(entityClass,primaryKey,lockMode,hints);
	}

	/**
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			LockModeType lockMode, Map<String, Object> properties) {
		
		Object[] pks = null;
		if(primaryKey.getClass().isArray()){
			pks = (Object[])primaryKey;
		}else{
			pks = new Object[]{primaryKey};
		}
		
		List<WhereCondition> condition = new ArrayList<WhereCondition>();
		//高速エンティティ
		if(FastEntity.class.isAssignableFrom(entityClass)){
			
			FastEntity entity = (FastEntity)ReflectionUtils.newInstance(entityClass);
			int i = 0;
			for(String e: entity.toPrimaryKeys().keySet()){
				condition.add(new WhereCondition(e,i,WhereOperand.Equal,pks[i]));
				i++;
			}
		//通常エンティティ	
		}else{
			List<Method> ms = ReflectionUtils.getAnotatedGetter(entityClass, Id.class);
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
				condition.add(new WhereCondition(name,i,WhereOperand.Equal,pk));
			}
		}
		final FreeSelectParameter parameter = new FreeSelectParameter();		
		sb.setConditionParameters(condition,new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
		
		OrmSelectParameter<T> ormParam = new OrmSelectParameter<T>(entityClass);
		ormParam.setLockModeType(lockMode);
		ormParam.setMaxSize(2);
		ormParam.getConditions().addAll(condition);
		String sql = sb.createSelect(ormParam);
		
		parameter.setEntityManager(this);
		parameter.setMaxSize(2);
		parameter.setLockMode(lockMode);
		parameter.setHints(properties);
		parameter.setSql(sql);
		parameter.setQueryId(entityClass.getSimpleName() + ".select");
		parameter.setResultType(entityClass);
		List<T> resultList = internalQuery.getResultList(parameter);		
		if(resultList.isEmpty()){
			return null;
		}else if(resultList.size() > 1){
			throw new NonUniqueResultException("too many rows entity = " + entityClass + " :pkvalue = " + primaryKey );
		}
		return resultList.get(0);
	}

	/**
	 * @see javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#flush()
	 */
	@Override
	public void flush() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#setFlushMode(javax.persistence.FlushModeType)
	 */
	@Override
	public void setFlushMode(FlushModeType flushMode) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#getFlushMode()
	 */
	@Override
	public FlushModeType getFlushMode() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public void lock(Object entity, LockModeType lockMode) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public void lock(Object entity, LockModeType lockMode,
			Map<String, Object> properties) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object entity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object, java.util.Map)
	 */
	@Override
	public void refresh(Object entity, Map<String, Object> properties) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public void refresh(Object entity, LockModeType lockMode) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object, javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public void refresh(Object entity, LockModeType lockMode,
			Map<String, Object> properties) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#clear()
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#detach(java.lang.Object)
	 */
	@Override
	public void detach(Object entity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object entity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#getLockMode(java.lang.Object)
	 */
	@Override
	public LockModeType getLockMode(Object entity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String propertyName, Object value) {
		hints.put(propertyName, value);
	}

	/**
	 * @see javax.persistence.EntityManager#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return hints;
	}

	/**
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String)
	 */
	@Override
	public Query createQuery(String qlString) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#createQuery(javax.persistence.criteria.CriteriaQuery)
	 */
	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String)
	 */
	@Override
	public Query createNamedQuery(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String sqlString) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public Query createNativeQuery(String sqlString, @SuppressWarnings("rawtypes") Class resultClass) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#joinTransaction()
	 */
	@Override
	public void joinTransaction() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> cls) {
		if(Connection.class.equals(cls)){
			return cls.cast(getConnection());
		}else{
			throw new IllegalArgumentException(String.valueOf(cls));
		}
	}	

	/**
	 * @see javax.persistence.EntityManager#getDelegate()
	 */
	@Override
	public Object getDelegate() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#close()
	 */
	@Override
	public void close() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#isOpen()
	 */
	@Override
	public boolean isOpen() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#getTransaction()
	 */
	@Override
	public EntityTransaction getTransaction() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#getEntityManagerFactory()
	 */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#getCriteriaBuilder()
	 */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.persistence.EntityManager#getMetamodel()
	 */
	@Override
	public Metamodel getMetamodel() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return the connection 
	 */
	protected Connection getConnection(){
		try{
			return dataSource.getConnection();
		}catch(SQLException sqle){
			throw new IllegalStateException("failed to get connection ",sqle);
		}
	}
	
}
