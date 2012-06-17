/**
 * Copyright 2011 the original author
 */
package client.sql.elink.free.strategy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;



import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.queries.ScrollableCursor;
import org.eclipse.persistence.sessions.server.ClientSession;

import client.sql.elink.EntityManagerProvider;
import client.sql.elink.free.LazyList;
import client.sql.elink.free.SQLExceptionHandlerImpl;
import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeSelectParameter;
import client.sql.free.FreeUpsertParameter;
import client.sql.free.NativeResult;
import client.sql.free.ResultSetFilter;
import client.sql.free.strategy.InternalQuery;

import sqlengine.builder.SQLBuilder;
import sqlengine.exception.ExceptionHandler;
import sqlengine.executer.RecordFilter;
import sqlengine.executer.RecordHandlerFactory;
import sqlengine.executer.ResultSetHandler;
import sqlengine.executer.impl.RecordHandlerFactoryImpl;
import sqlengine.executer.impl.ResultSetHandlerImpl;
import sqlengine.facade.QueryResult;
import sqlengine.facade.SQLEngineFacade;
import sqlengine.facade.UpdateParameter;
import sqlengine.facade.impl.SQLEngineFacadeImpl;


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
	 * @see client.sql.free.strategy.InternalQuery#getResultList(client.sql.free.FreeSelectParameter)
	 */
	@Override
	public <T> List<T> getResultList(FreeSelectParameter parameter) {
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
	 * @see client.sql.internal.free.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeSelectParameter parameter) {
		parameter.setMaxSize(1);
		List<T> result = getResultList(parameter);
		return result.isEmpty() ? null : result.get(0);
	}

	/**
	 * @see client.sql.free.strategy.InternalQuery#getTotalResult(client.sql.free.FreeSelectParameter)
	 */
	@Override
	public NativeResult getTotalResult(final FreeSelectParameter parameter) {

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
	 * @see client.sql.elink.internal.free.AbstractInternalNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(FreeSelectParameter parameter) {
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
	protected Query createQuery(FreeSelectParameter param) {
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
	protected Query mapping(FreeSelectParameter parameter,Query query){
				
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
	 * @see client.sql.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeSelectParameter param){		

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
	 * @see client.sql.free.strategy.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeUpsertParameter param) {
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
	protected Query createNamedQuery(String executingQuery,FreeSelectParameter param){
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
	protected String buildSql(List<Object> bindList, FreeQueryParameter param){
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
	 * @see client.sql.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeUpsertParameter> param) {
		
		//TODO 要検証		
		
		List<UpdateParameter> engineParams = new ArrayList<UpdateParameter>();
		for(FreeQueryParameter p: param){
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
