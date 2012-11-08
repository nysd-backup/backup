/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import alpha.jdbc.domain.LazyList;
import alpha.jdbc.domain.PreparedQuery;
import alpha.jdbc.domain.ResultSetWrapper;
import alpha.jdbc.domain.StatementWrapper;
import alpha.jdbc.domain.TotalData;
import alpha.jdbc.exception.ExceptionHandler;
import alpha.jdbc.exception.impl.ExceptionHandlerImpl;
import alpha.jdbc.service.ModifyingRequest;
import alpha.jdbc.service.QueryRequest;
import alpha.jdbc.service.QueryService;
import alpha.jdbc.service.ReadingRequest;
import alpha.jdbc.strategy.QueryBuilder;
import alpha.jdbc.strategy.RecordHandlerFactory;
import alpha.jdbc.strategy.ResultSetHandler;
import alpha.jdbc.strategy.Selector;
import alpha.jdbc.strategy.StatementProvider;
import alpha.jdbc.strategy.Updater;
import alpha.jdbc.strategy.impl.QueryBuilderProxyImpl;
import alpha.jdbc.strategy.impl.RecordHandlerFactoryImpl;
import alpha.jdbc.strategy.impl.ResultSetHandlerImpl;
import alpha.jdbc.strategy.impl.SelectorImpl;
import alpha.jdbc.strategy.impl.StatementProviderImpl;
import alpha.jdbc.strategy.impl.UpdaterImpl;




/**
 * The facade of the SQLEngine
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryServiceImpl implements QueryService{

	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
	
	/** the ResultSetHandler */
	private ResultSetHandler resultSetHandler = new ResultSetHandlerImpl();

	/** the QueryBuilder */
	private QueryBuilder queryBuilder = new QueryBuilderProxyImpl();
	
	/** the StatementProvider */
	private StatementProvider provider = new StatementProviderImpl();
	
	/** the RecordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** the selector */
	private Selector selector = new SelectorImpl();
	
	/** the updater*/
	private Updater updater = new UpdaterImpl();

	/**
	 * @param exceptionHandler the exceptionHandler to set
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler){
		this.exceptionHandler = exceptionHandler;
	}
	
	/**
	 * @param recordHandlerFactory the recordHandlerFactory to set
	 */
	public void setRecordHandlerFactory(RecordHandlerFactory recordHandlerFactory){
		this.recordHandlerFactory = recordHandlerFactory;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(StatementProvider provider){
		this.provider = provider;
	}
	
	/**
	 * @param resultSetHandler the resultSetHandler to set
	 */
	public void setResultSetHandler(ResultSetHandler resultSetHandler){
		this.resultSetHandler = resultSetHandler;
	}
	
	/**
	 * @param queryBuilder the queryBuilder to set
	 */
	public void setQueryBuilder(QueryBuilder queryBuilder){
		this.queryBuilder = queryBuilder;
	}

	/**
	 * @param selector the selector to set
	 */
	public void setSelector(Selector selector) {
		this.selector = selector;
	}
	
	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(Updater updater) {
		this.updater = updater;
	}
	
	/**
	 * @see alpha.jdbc.service.QueryService#executeCount(alpha.jdbc.service.ReadingRequest, java.sql.Connection)
	 */
	@Override
	public long executeCount(ReadingRequest param,Connection con) {
		
		PreparedQuery preparedQuery = prepare(param);			
		StatementWrapper stmt = null;		
		ResultSetWrapper rs = null;
		try{
			stmt = preparedQuery.getStatement(con, provider);	
			stmt.configure(0,param.getFetchSize(),param.getTimeoutSeconds());
			return stmt.read(selector).getOnlyOne(Number.class).longValue();
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * @see alpha.jdbc.service.QueryService#executeQuery(alpha.jdbc.service.ReadingRequest, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeQuery(ReadingRequest param , Connection con){	

		PreparedQuery preparedQuery = prepare(param);		
		StatementWrapper stmt = null;		
		ResultSetWrapper rs = null;
		try{									
			int maxRows = param.getMaxSize() != 0 ? param.getFirstResult() + param.getMaxSize() : 0;			
			stmt = preparedQuery.getStatement(con, provider);	
			stmt.configure(maxRows,param.getFetchSize(),param.getTimeoutSeconds());
			
			rs= stmt.read(selector);
			rs.setStartPosition(param.getFirstResult());
			rs.setFilter(param.getFilter());
			@SuppressWarnings("unchecked")
			List<T> result = rs.loadIntoMemory(param.getResultType(),resultSetHandler);
			return result;
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * @see alpha.jdbc.service.QueryService#executeFetch(alpha.jdbc.service.ReadingRequest, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeFetch(ReadingRequest param,Connection con) {

		PreparedQuery preparedQuery = prepare(param);		
		ResultSetWrapper rs = null;
		StatementWrapper stmt = null;		
		try{							
			int maxRows = param.getMaxSize() != 0 ? param.getFirstResult() + param.getMaxSize() : 0;
			stmt = preparedQuery.getStatement(con, provider);	
			stmt.configure(maxRows,param.getFetchSize(),param.getTimeoutSeconds());
			
			rs= stmt.read(selector);
			rs.setStartPosition(param.getFirstResult());
			@SuppressWarnings("unchecked")
			LazyList<T> resultList = rs.getLazyList(exceptionHandler,recordHandlerFactory,param.getResultType());
			return resultList;
			
		}catch(SQLException sqle){
			close(rs,stmt);
			throw exceptionHandler.rethrow(sqle);
		}catch(Exception t){
			close(rs,stmt);
			throw new RuntimeException(t);
		}
	}

	/**
	 * @see alpha.jdbc.service.QueryService#executeTotalQuery(alpha.jdbc.service.ReadingRequest, java.sql.Connection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TotalData executeTotalQuery(ReadingRequest param,Connection con) {
		PreparedQuery preparedQuery = prepare(param);
		StatementWrapper stmt = null;		
		ResultSetWrapper rs = null;
		try{															
			stmt = preparedQuery.getStatement(con, provider);	
			stmt.configure(0,param.getFetchSize(),param.getTimeoutSeconds());

			rs = stmt.read(selector);
			rs.setStartPosition(param.getFirstResult());
			rs.setFilter(param.getFilter());							
			return rs.loadIntoMemory(param.getResultType(),resultSetHandler,param.getMaxSize());
			
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}

	}

	/**
	 * @see alpha.jdbc.service.QueryService#executeUpdate(alpha.jdbc.service.ModifyingRequest, java.sql.Connection)
	 */
	@Override
	public int executeUpsert(ModifyingRequest param, Connection con) {
		
		//SQL生成
		PreparedQuery preparedQuery = prepare(param);
		StatementWrapper stmt = null;
		
		try{			
			stmt = preparedQuery.getStatement(con, provider);	
			stmt.configure(0,0,param.getTimeoutSeconds());
			return stmt.modify(updater);
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(stmt);
		}
	}
	
	/**
	 * @see alpha.jdbc.service.QueryService#executeBatch(alpha.jdbc.service.BatchParameter, java.sql.Connection)
	 */
	@Override
	public int[] executeBatch(List<ModifyingRequest> param, Connection con) {
		
		//先頭行で作成されたSQLを使用する		
		ModifyingRequest base = param.get(0);
		
		//SQL生成、if禁止
		String executingSql  = base.getSql();
		if(!base.isUseRowSql()){
			executingSql = queryBuilder.build(base.getSqlId(), executingSql);					
		}				
	
		List<Map<String,Object>> parameters = new ArrayList<Map<String,Object>>();
		for(ModifyingRequest p : param){
			parameters.add(p.getParameter());
		}
		PreparedQuery preparedQuery = queryBuilder.prepare(executingSql, parameters,base.getWrappingClause(),base.getSqlId());						
		StatementWrapper stmt = null;
		
		try{
			//ステートメント			
			stmt = preparedQuery.getStatement(con, provider);	
			stmt.configure(0,0,base.getTimeoutSeconds());
			return stmt.batchModify(updater);
	
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(stmt);
		}
	}

	/**
	 * Creates the SQL.
	 * 
	 * @param <T>　the type
	 * @param param the parameter
	 * @param bindList the bindList
	 * @return the query
	 */
	@SuppressWarnings("unchecked")
	private PreparedQuery prepare(QueryRequest param){
		String executingSql  = param.getSql();
		if(!param.isUseRowSql()){
			executingSql = queryBuilder.build(param.getSqlId(), executingSql);		
			executingSql = queryBuilder.evaluate(executingSql, param.getParameter(),param.getSqlId());
		}						
		return queryBuilder.prepare(executingSql, Arrays.asList(param.getParameter()),param.getWrappingClause(), param.getSqlId());				
	}

	/**
	 * Close.
	 * 
	 * @param rs the rs
	 * @param stmt the statement
	 */
	private void close(ResultSetWrapper rs , StatementWrapper stmt){
		try{
			if(rs != null){
				rs.close();
			}		
		}finally{
			close(stmt);
		}
	}
	
	/**
	 * Close.
	 * 
	 * @param stmt the statement
	 */
	private void close(StatementWrapper stmt){
		if( stmt != null){
			stmt.close();
		}		
	}

}
