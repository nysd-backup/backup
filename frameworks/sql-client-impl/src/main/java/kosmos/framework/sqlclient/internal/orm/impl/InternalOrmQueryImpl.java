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

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.free.NativeQuery;
import kosmos.framework.sqlclient.api.free.NativeUpdate;
import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.free.impl.InternalQueryImpl;
import kosmos.framework.sqlclient.internal.free.impl.LocalQueryEngineImpl;
import kosmos.framework.sqlclient.internal.free.impl.LocalUpdateEngineImpl;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
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
	
	/** the queryFactory */
	private SQLEngineFacade sqlEngineFacade;
	
	/** the ConnectionProvider */
	private ConnectionProvider cs;
	
	/**
	 * @param engineFacade the engineFacade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade engineFacade){
		this.sqlEngineFacade = engineFacade;
	}
	
	/**
	 * @param provider the provider to set
	 */
	public void setConnectionProvider(ConnectionProvider provider){
		this.cs = provider;
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
	public <E> E find(OrmQueryContext<E> context,Object... pks) {
		
		Field[] fs = ReflectionUtils.getAllAnotatedField(context.getEntityClass(), Id.class);
		if(fs.length != pks.length){
			throw new IllegalArgumentException("invalid primary key count");
		}
		
		OrmQueryContext<E> newContext = new OrmQueryContext<E>(context.getEntityClass());
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
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#getResultList(kosmos.framework.sqlclient.api.orm.OrmQueryContext)
	 */
	@Override
	public <E> List<E> getResultList(OrmQueryContext<E> condition) {
		
		String sql = sb.createSelect(condition);
		InternalQueryImpl query = new InternalQueryImpl(true, sql, condition.getEntityClass().getName()+".select", cs,
				condition.getEntityClass(), sqlEngineFacade);
		final NativeQuery engine = new LocalQueryEngineImpl(query);

		setConditionParameters(condition,new Bindable(){
			public void setParameter(String key , Object value){
				engine.setParameter(key, value);
			}
		});
		
		for(Map.Entry<String, Object> e: condition.getHints().entrySet()){
			engine.setHint(e.getKey(), e.getValue());
		}
		
		engine.setFirstResult( condition.getFirstResult());			
		engine.setMaxResults(condition.getMaxSize());
		return engine.getResultList();
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#insert(java.lang.Object)
	 */
	@Override
	public int insert(OrmContext<?> context,Map<String,Object> values) {
		
		String sql = sb.createInsert(context.getEntityClass(),values.keySet());
		NativeUpdate engine = createUpdateEngine(sql, context.getEntityClass().getName()+".insert");

		//更新値設定
		for(Map.Entry<String, Object> e: values.entrySet()){
			engine.setParameter(e.getKey(), e.getValue());
		}
		
		setUpdatingHint(context.getHints(),engine);
		return engine.update();
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#update(kosmos.framework.sqlclient.api.orm.OrmQueryContext, java.util.Map)
	 */
	@Override
	public int update(OrmContext<?> condition, Map<String, Object> set) {
		String sql = sb.createUpdate(condition, set.keySet());		
		NativeUpdate engine = createUpdateEngine(condition, sql, condition.getEntityClass().getName()+".update");
		
		//set statement
		for(Map.Entry<String, Object> v: set.entrySet()){
			engine.setParameter(v.getKey(),v.getValue());
		}
		
		return engine.update();
	}


	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#delete(kosmos.framework.sqlclient.api.orm.OrmQueryContext)
	 */
	@Override
	public int delete(OrmContext<?> condition) {
		String sql = sb.createDelete(condition);
		NativeUpdate engine = createUpdateEngine(condition, sql, condition.getEntityClass().getName()+".delete");
		return engine.update();
	}
	
	/**
	 * Creates the updating engine.
	 * 
	 * @param condition the condition
	 * @param sql the sql
	 * @param sqlId the sqlId
	 * @return the updating Engine
	 */
	protected NativeUpdate createUpdateEngine(OrmContext<?> condition, String sql , String sqlId){
		
		final NativeUpdate engine = createUpdateEngine(sql, sqlId);
		setConditionParameters(condition, new Bindable(){
			public void setParameter(String key , Object value){
				engine.setParameter(key, value);
			}
		});
		setUpdatingHint(condition.getHints(), engine);
		return engine;
	}
	
	/**
	 * Sets the hints.
	 * 
	 * @param hints the hints 
	 * @param engine the engine
	 */
	protected void setUpdatingHint(Map<String,Object> hints,NativeUpdate engine){
		for(Map.Entry<String, Object> e: hints.entrySet()){
			engine.setHint(e.getKey(), e.getValue());
		}
	}
	
	/**
	 * Creates the updating engine.
	 * 
	 * @param sql the sql
	 * @param sqlId the sqlId
	 * @return the updating Engine
	 */
	protected NativeUpdate createUpdateEngine(String sql , String sqlId){
		InternalQuery query = new InternalQueryImpl(true, sql, sqlId, cs, null, sqlEngineFacade);
		return  new LocalUpdateEngineImpl(query);
	}

	/**
	 * Set the parameter to delegate.
	 * 
	 * @param condition the condition
	 * @param delegate the delegate
	 */
	protected <E> void setConditionParameters(OrmContext<E> condition, Bindable delegate){
		//簡易フィルターが設定されている場合、実行時に設定されたパラメータを使用する
		if(condition.getFilterString() != null){
			Object[] params = condition.getEasyParams();
			if(params != null){
				for(int i = 0; i < params.length; i++){
					delegate.setParameter(String.format("p%d", i+1),params[i]);
				}
			}
			return ;
		}
		//検索条件
		List<WhereCondition> conds = condition.getConditions();
	
		for(WhereCondition cond : conds){
			if(WhereOperand.IN == cond.getOperand()){
				List<?> val = List.class.cast(cond.getValue());
				int cnt = -1;
				for(Object v : val){
					cnt++;
					delegate.setParameter(String.format("%s_%d_%d", cond.getColName(),cond.getBindCount(),cnt),v);
				}
			}else{	
				delegate.setParameter(String.format("%s%d", cond.getColName(), cond.getBindCount()),cond.getValue());
				if( WhereOperand.Between == cond.getOperand()){
					delegate.setParameter(String.format( "%s%d_to",cond.getColName(),cond.getBindCount()), cond.getToValue());
				}
			}
		}
	}
	
	private static interface Bindable {
		
		/**
		 * @param key
		 * @param value
		 */
		public void setParameter(String key , Object value);
	}


}
