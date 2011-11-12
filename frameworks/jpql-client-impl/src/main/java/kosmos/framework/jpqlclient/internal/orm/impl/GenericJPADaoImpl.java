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
import kosmos.framework.jpqlclient.api.orm.JPAOrmCondition;
import kosmos.framework.jpqlclient.internal.free.impl.InternalNamedQueryImpl;
import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedQueryEngine;
import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedUpdateEngine;
import kosmos.framework.jpqlclient.internal.orm.GenericDao;
import kosmos.framework.jpqlclient.internal.orm.JPQLStatementBuilder;
import kosmos.framework.sqlclient.api.EmptyHandler;
import kosmos.framework.sqlclient.api.MultiResultHandler;
import kosmos.framework.sqlclient.api.orm.WhereCondition;
import kosmos.framework.sqlclient.api.orm.WhereOperand;
import kosmos.framework.sqlclient.internal.impl.DefaultEmptyHandlerImpl;
import kosmos.framework.sqlclient.internal.impl.DefaultMultiResultHandlerImpl;
import kosmos.framework.sqlengine.builder.ConstAccessor;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.builder.impl.ConstAccessorImpl;
import kosmos.framework.sqlengine.builder.impl.SQLBuilderProxyImpl;


/**
 * The generic DAO for JPA.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class GenericJPADaoImpl implements GenericDao {

	/** the EntityManager */
	private EntityManager em = null;
	
	/** the EmptyHandler */
	private EmptyHandler eh = new DefaultEmptyHandlerImpl();
	
	/** the MultiResultHandler */
	private MultiResultHandler mrh = new DefaultMultiResultHandlerImpl();
	
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
	 * @param handler the handler to set
	 */
	public void setEmptyHandler(EmptyHandler handler){
		eh = handler;	
	}

	/**
	 * @param handler the handler to set
	 */
	public void setMultiResultHandler(MultiResultHandler handler){
		mrh = handler;	
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.GenericDao#updateAny(kosmos.framework.jpqlclient.api.orm.JPAOrmCondition, java.util.Map)
	 */
	@Override
	public int updateAny(JPAOrmCondition<?> condition , Map<String,Object> set){

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
	 * @see kosmos.framework.jpqlclient.internal.orm.GenericDao#deleteAny(kosmos.framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public  int deleteAny(JPAOrmCondition<?> condition){		
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
	 * @see kosmos.framework.jpqlclient.internal.orm.GenericDao#find(kosmos.framework.jpqlclient.api.orm.JPAOrmCondition, java.lang.Object[])
	 */
	@Override
	public <E> E find(JPAOrmCondition<E> query,Object... pks) {
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
		if( result == null && query.isNoDataErrorEnabled()){
			eh.handleEmptyResult(query.getEntityClass());			
		}
		return result;
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.orm.GenericDao#getResultList(kosmos.framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public <E> List<E> getResultList(JPAOrmCondition<E> entityQuery){	
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
	 * @see kosmos.framework.jpqlclient.internal.orm.GenericDao#findAny(kosmos.framework.jpqlclient.api.orm.JPAOrmCondition)
	 */
	@Override
	public <E> E findAny(JPAOrmCondition<E> entityQuery){
		NamedQuery query = createJPAQuery(entityQuery);
		query.setMaxResults(2);
		List<E> entityList = query.getResultList();
		if( entityList.size() == 1){
			E result = entityList.get(0);
			return result;
		}else if( entityList.size() >= 2){
			mrh.handleResult(entityQuery);
		}
		return null;
	}
	
	/**
	 * Creates the named query.
	 * 
	 * @param <E> the type
	 * @param entityQuery　the entityQuery
	 * @return the named query
	 */
	protected <E> NamedQuery createJPAQuery(JPAOrmCondition<E> entityQuery){
		
		//クエリ作�E
		final NamedQuery delegate = createEngine(entityQuery);

		if(entityQuery.isNoDataErrorEnabled()){
			delegate.enableNoDataError();
		}
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
	protected <E> NamedQuery createEngine(JPAOrmCondition<E> entityQuery){
		String statement = createStatementBuilder().createSelect(entityQuery);
		InternalNamedQueryImpl impl = new InternalNamedQueryImpl(null,statement, em, String.format("Select.%s",entityQuery.getEntityClass().getSimpleName()),true,builder,accessor);
		LocalNamedQueryEngine delegate = new LocalNamedQueryEngine(impl,eh);
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
	protected <E> void setConditionParameters(JPAOrmCondition<E> condition, Bindable delegate){
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
