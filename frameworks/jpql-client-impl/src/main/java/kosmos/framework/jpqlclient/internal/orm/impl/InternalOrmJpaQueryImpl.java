/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.jpqlclient.api.free.NamedQuery;
import kosmos.framework.jpqlclient.api.free.NamedUpdate;
import kosmos.framework.jpqlclient.internal.free.impl.InternalNamedQueryImpl;
import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedQueryEngine;
import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedUpdateEngine;
import kosmos.framework.jpqlclient.internal.orm.JPQLStatementBuilder;
import kosmos.framework.sqlclient.api.orm.OrmContext;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.sqlengine.builder.ConstAccessor;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.builder.impl.ConstAccessorImpl;
import kosmos.framework.sqlengine.builder.impl.SQLBuilderProxyImpl;


/**
 * Creates the JPQL and execute 
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalOrmJpaQueryImpl implements InternalOrmQuery {

	/** the EntityManager */
	private EntityManager em = null;
	
	/** the ConstAccessor */
	private ConstAccessor accessor = new ConstAccessorImpl();
	
	/** the SQLBuilder */
	private SQLBuilder builder = new SQLBuilderProxyImpl();

	/**
	 * @param builder the builder to set
	 */
	public void setSqlBuilder(SQLBuilder builder){
		this.builder = builder;
	}
	
	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(ConstAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @param provider the provider to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();		
	}	
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.InternalJPAOrmQuery#updateAny(kosmos.framework.jpqlclient.api.orm.OrmQueryCondition, java.util.Map)
	 */
	@Override
	public int update(OrmContext<?> condition , Map<String,Object> set){

		JPQLStatementBuilder stmt = createStatementBuilder();
		String updateJpql = stmt.createUpdate(condition, set.keySet());
		InternalNamedQueryImpl delegate = new InternalNamedQueryImpl(null,updateJpql, em, String.format("Update.%s",condition.getEntityClass().getSimpleName()), true,builder,accessor);
		final NamedUpdate engine = createNamedUpdateEngine(delegate);

		setConditionParameters(condition, new Bindable(){
			public void setParameter(String key , Object value){
				engine.setParameter(key, value);
			}
		});
		for(Map.Entry<String, Object> s : set.entrySet()){
			engine.setParameter(s.getKey(),s.getValue());			
		}		
		Map<String,Object> hints = condition.getHints();
		for(Map.Entry<String, Object> e : hints.entrySet()){
			engine.setHint(e.getKey(), e.getValue());
		}
		return engine.update();
	}

	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.InternalJPAOrmQuery#deleteAny(kosmos.framework.jpqlclient.api.orm.JPAOrmQueryCondition)
	 */
	@Override
	public  int delete(OrmContext<?> condition){		
		JPQLStatementBuilder stmt = createStatementBuilder();
		String updateJpql = stmt.createDelete(condition);
		InternalNamedQueryImpl delegate = new InternalNamedQueryImpl(null,updateJpql, em, String.format("Update.%s",condition.getEntityClass().getSimpleName()), true,builder,accessor);
		final NamedUpdate engine = createNamedUpdateEngine(delegate);

		setConditionParameters(condition, new Bindable(){
			public void setParameter(String key , Object value){
				engine.setParameter(key, value);
			}
		});
		Map<String,Object> hints = condition.getHints();
		for(Map.Entry<String, Object> e : hints.entrySet()){
			engine.setHint(e.getKey(), e.getValue());
		}
		return engine.update();
	}

	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.InternalJPAOrmQuery#find(kosmos.framework.jpqlclient.api.orm.JPAOrmQueryCondition, java.lang.Object[])
	 */
	@Override
	public <E> E find(OrmQueryContext<E> query,Object... pks) {
		Object v = pks;
		if( pks.length == 1){
			v = pks[0];
		}
		E result = null;
		if(query.getLockModeType() != null){
			result = (E)em.find(query.getEntityClass(),v,query.getLockModeType(),query.getHints());
		}else{
			result = (E)em.find(query.getEntityClass(),v,query.getHints()); 
		}
		return result;
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.InternalJPAOrmQuery#getResultList(kosmos.framework.jpqlclient.api.orm.JPAOrmQueryCondition)
	 */
	@Override
	public <E> List<E> getResultList(OrmQueryContext<E> entityQuery){	
		NamedQuery query = createJPAQuery(entityQuery);
		int first = entityQuery.getFirstResult();
		if( first > 0){
			query.setFirstResult(first);
		}
		int max = entityQuery.getMaxSize();
		if(max > 0){			
			query.setMaxResults(max);
		}
		return query.getResultList();
	}
	
	/**
	 * Creates the named query.
	 * 
	 * @param <E> the type
	 * @param entityQuery　the entityQuery
	 * @return the named query
	 */
	protected <E> NamedQuery createJPAQuery(OrmQueryContext<E> entityQuery){
		
		//クエリ作�E
		final NamedQuery delegate = createEngine(entityQuery);

		if(entityQuery.getLockModeType() != null){
			delegate.setLockMode(entityQuery.getLockModeType());
		}

		setConditionParameters(entityQuery,new Bindable(){
			public void setParameter(String key , Object value){
				delegate.setParameter(key, value);
			}
		});
		
		Map<String,Object> hints = entityQuery.getHints();
		for(Map.Entry<String, Object> e : hints.entrySet()){
			delegate.setHint(e.getKey(), e.getValue());
		}
		return delegate;
	}
	
	/**
	 * Creates the named query.
	 * 
	 * @param <E>　the type
	 * @param entityQuery the entityQuery
	 * @return the query
	 */
	protected <E> NamedQuery createEngine(OrmQueryContext<E> entityQuery){
		String statement = createStatementBuilder().createSelect(entityQuery);
		InternalNamedQueryImpl impl = new InternalNamedQueryImpl(null,statement, em, String.format("Select.%s",entityQuery.getEntityClass().getSimpleName()),true,builder,accessor);
		LocalNamedQueryEngine delegate = new LocalNamedQueryEngine(impl);
		return delegate;
	}
	
	/**
	 * Creates the statement builder.
	 * 
	 * @return the statement builder
	 */
	protected JPQLStatementBuilder createStatementBuilder(){
		return new JPQLStatementBuilderImpl();
	}
	
	/**
	 * Creates the named updater.
	 * 
	 * @param delegate the delegate
	 * @return the engine
	 */
	protected NamedUpdate createNamedUpdateEngine(InternalNamedQueryImpl delegate){
		return new LocalNamedUpdateEngine(delegate);
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

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#insert(java.lang.Object, java.util.Map)
	 */
	@Override
	public int insert(Object entity, Map<String, Object> hints) {
		throw new UnsupportedOperationException("Dont' use this method. Use 'EntityManager#persist' instead");
	}

}
