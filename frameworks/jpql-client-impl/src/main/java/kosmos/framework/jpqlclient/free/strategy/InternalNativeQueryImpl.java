/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.free.strategy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.jpqlclient.free.LazyList;
import kosmos.framework.jpqlclient.free.SQLExceptionHandlerImpl;
import kosmos.framework.sqlclient.free.FreeParameter;
import kosmos.framework.sqlclient.free.FreeQueryParameter;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.free.NativeResult;
import kosmos.framework.sqlclient.free.ResultSetFilter;
import kosmos.framework.sqlclient.free.strategy.InternalQuery;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.exception.ExceptionHandler;
import kosmos.framework.sqlengine.executer.RecordFilter;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.ResultSetHandler;
import kosmos.framework.sqlengine.executer.impl.RecordHandlerFactoryImpl;
import kosmos.framework.sqlengine.executer.impl.ResultSetHandlerImpl;
import kosmos.framework.sqlengine.facade.QueryResult;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.UpdateParameter;
import kosmos.framework.sqlengine.facade.impl.SQLEngineFacadeImpl;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.queries.ScrollableCursor;
import org.eclipse.persistence.sessions.server.ClientSession;


/**
 * An internal query using EclipseLink.
 * 
 * <pre>
 * 	Gets the result binded not to EntiyBean but to POJO.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class InternalNativeQueryImpl implements InternalQuery {
	
	/** the EntityManager */
	private EntityManager em;
	
	/** the <code>SQLBuilder</code> */
	private SQLBuilder builder;

	/** the ResultSetHandler */
	private ResultSetHandler handler = new ResultSetHandlerImpl();
	
	/** the RecordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new SQLExceptionHandlerImpl();
	
	/** the engine facade */
	private SQLEngineFacade facade = new SQLEngineFacadeImpl();
	
	/**
	 * @param facade the facade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.facade = facade;
	}
	
	/**
	 * @param em the em to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider em){
		this.em = em.getEntityManager();
	}
	
	/**
	 * @param builder the builder to set
	 */
	public void setSqlBuilder(SQLBuilder builder){
		this.builder = builder;
	}
	
	/**
	 * @param exceptionHandler the exceptionHandler to set
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler){
		this.exceptionHandler = exceptionHandler;
	}
	
	/**
	 * @param handler the handler to set
	 */
	public void setResultSetHandler(ResultSetHandler handler){
		this.handler = handler;
	}
	
	/**
	 * @param handler the recordHandlerFactory to set
	 */
	public void setRecordHandlerFactory(RecordHandlerFactory recordHandlerFactory){
		this.recordHandlerFactory = recordHandlerFactory;
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getResultList(kosmos.framework.sqlclient.free.FreeQueryParameter)
	 */
	@Override
	public <T> List<T> getResultList(FreeQueryParameter parameter) {
		Query query = mapping(parameter,createQuery(parameter));	
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		
		try {
			final ResultSetFilter filter = parameter.getFilter();
			RecordFilter recordFilter = null;
			if(filter != null){
				recordFilter = new RecordFilter(){
					@Override
					public <K> K edit(K data) {
						return filter.edit(data);
					}					
				};
			}			
			return handler.getResultList(rs, parameter.getResultType(), recordFilter);
		}catch (SQLException e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeQueryParameter parameter) {
		parameter.setMaxSize(1);
		List<T> result = getResultList(parameter);
		return result.isEmpty() ? null : result.get(0);
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getTotalResult(kosmos.framework.sqlclient.free.FreeQueryParameter)
	 */
	@Override
	public NativeResult getTotalResult(final FreeQueryParameter parameter) {

		Query query = mapping(parameter,createQuery(parameter));
		query.setMaxResults(0);
		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		QueryResult result;
		try {
			final ResultSetFilter filter = parameter.getFilter();
			RecordFilter recordFilter = null;
			if(filter != null){
				recordFilter = new RecordFilter(){
					@Override
					public <K> K edit(K data) {
						return filter.edit(data);
					}					
				};
			}			
			result = handler.getResultList(rs, parameter.getResultType(),recordFilter,parameter.getMaxSize(),parameter.getFirstResult());
		}catch (SQLException e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
		return new NativeResult(result.isLimited(),result.getResultList(),result.getHitCount());
		
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.free.AbstractInternalNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(FreeQueryParameter parameter) {
		Query query = mapping(parameter,createQuery(parameter));		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		try{
			return new LazyList(cursor, recordHandlerFactory.create(parameter.getResultType(), cursor.getResultSet()),exceptionHandler);
		} catch (SQLException e) {
			cursor.close();
			throw exceptionHandler.rethrow(e);
		}	
	}
	
	/**
	 * Creates the query.
	 * 
	 * @return the query
	 */
	protected Query createQuery(FreeQueryParameter param) {
		List<Object> bindList = new ArrayList<Object>();
		String executingSql = buildSql(bindList,param);		
		Query query = param.getName() != null ? createNamedQuery(executingSql, param) : em.createNativeQuery(executingSql);
		return bindParmaeterToQuery(query, bindList);			
	}
	
	/**
	 * Mapping the hint.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query mapping(FreeQueryParameter parameter,Query query){
				
		for(Map.Entry<String, Object> h : parameter.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
		if(parameter.getFirstResult() > 0){
			query.setFirstResult(parameter.getFirstResult());
		}
		if(parameter.getMaxSize() > 0){
			query.setMaxResults(parameter.getMaxSize());
		}
		
		//ResultSetの取得を可能とする。
		query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
		return query;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeQueryParameter param){		

		List<Object> bindList = new ArrayList<Object>();
		String executingSql = buildSql(bindList,param);
		
		//countの場合は範囲設定無効とする。
		executingSql = builder.setCount(executingSql);
		Query query = param.getName() != null ? createNamedQuery(executingSql,param) : em.createNativeQuery(executingSql);
		query = bindParmaeterToQuery(query, bindList);	
		
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		Object value = query.getSingleResult();
		return Long.parseLong(value.toString());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeUpdateParameter param) {
		List<Object> bindList = new ArrayList<Object>();
		Query query = null;
		if(param.getName() != null){
			query = em.createNamedQuery(param.getName());			
		}else {
			String executingSql = buildSql(bindList,param);
			query = em.createNativeQuery(executingSql);
		}
		query = bindParmaeterToQuery(query, bindList);			
		
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}	

		return query.executeUpdate();
	}
	

	/**
	 * Creates the named query.
	 * 
	 * @return the query
	 */
	protected Query createNamedQuery(String executingQuery,FreeQueryParameter param){
		Query query = em.createNamedQuery(executingQuery);
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
		if(param.getFirstResult() > 0){
			query.setFirstResult(param.getFirstResult());
		}
		if(param.getMaxSize() > 0){
			query.setMaxResults(param.getMaxSize());
		}
		return query;
	}
	
	/**
	 * Builds the SQL.
	 * 
	 * @param bindList the bind parameters
	 * @return the SQL
	 */
	@SuppressWarnings("unchecked")
	protected String buildSql(List<Object> bindList, FreeParameter param){
		String str = param.getSql();	
		if(!param.isUseRowSql()){
			str = builder.build(param.getQueryId(), str);
			str = builder.evaluate(str, param.getBranchParam(),param.getQueryId());
		}			
		str = builder.replaceToPreparedSql(str, Arrays.asList(param.getParam()),Arrays.asList(bindList), param.getQueryId());			
		return str;
	}
	
	/**
	 * Bind the parameter to query.
	 * 
	 * @param query the query
	 * @param bindList the bind parameters
	 * @return the query
	 */
	protected Query bindParmaeterToQuery(Query query,List<Object> bindList){
		
		for(int i=0; i < bindList.size(); i++){			
			query.setParameter(i+1,bindList.get(i));			
		}		
		return query;
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeUpdateParameter> param) {
		
		//TODO 要検証		
		
		List<UpdateParameter> engineParams = new ArrayList<UpdateParameter>();
		for(FreeParameter p: param){
			UpdateParameter ep = new UpdateParameter();
			ep.setAllParameter(p.getParam());
			ep.setAllBranchParameter(p.getBranchParam());
			ep.setSqlId(p.getQueryId());
			ep.setSql(p.getSql());
			ep.setUseRowSql(p.isUseRowSql());	
			Object value = p.getHints().get(QueryHints.JDBC_TIMEOUT);
			if( value != null){
				ep.setTimeoutSeconds((Integer)value);
			}
			engineParams.add(ep);
		}		
		
		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();		
		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
		Connection con = accessor.getConnection();
		
		return facade.executeBatch(engineParams, con);
		
	}

}
