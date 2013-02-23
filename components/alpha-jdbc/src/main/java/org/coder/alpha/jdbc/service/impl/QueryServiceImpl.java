/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.coder.alpha.jdbc.domain.LazyList;
import org.coder.alpha.jdbc.domain.PreparedQuery;
import org.coder.alpha.jdbc.domain.ResultSetWrapper;
import org.coder.alpha.jdbc.domain.StatementWrapper;
import org.coder.alpha.jdbc.domain.TotalList;
import org.coder.alpha.jdbc.exception.ExceptionHandler;
import org.coder.alpha.jdbc.exception.impl.DefaultExceptionHandler;
import org.coder.alpha.jdbc.service.ModifyingRequest;
import org.coder.alpha.jdbc.service.QueryRequest;
import org.coder.alpha.jdbc.service.QueryService;
import org.coder.alpha.jdbc.service.ReadingRequest;
import org.coder.alpha.jdbc.strategy.QueryLoader;
import org.coder.alpha.jdbc.strategy.RecordHandlerFactory;
import org.coder.alpha.jdbc.strategy.ResultSetHandler;
import org.coder.alpha.jdbc.strategy.Selector;
import org.coder.alpha.jdbc.strategy.StatementProvider;
import org.coder.alpha.jdbc.strategy.Updater;
import org.coder.alpha.jdbc.strategy.impl.DefaultRecordHandlerFactory;
import org.coder.alpha.jdbc.strategy.impl.DefaultResultSetHandler;
import org.coder.alpha.jdbc.strategy.impl.DefaultSelector;
import org.coder.alpha.jdbc.strategy.impl.DefaultStatementProvider;
import org.coder.alpha.jdbc.strategy.impl.DefaultUpdater;
import org.coder.alpha.jdbc.strategy.impl.QueryLoaderTrace;





/**
 * The facade of the SQLEngine
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryServiceImpl implements QueryService{

	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	
	/** the ResultSetHandler */
	private ResultSetHandler resultSetHandler = new DefaultResultSetHandler();

	/** the QueryLoader */
	private QueryLoader queryBuilder = new QueryLoaderTrace();
	
	/** the StatementProvider */
	private StatementProvider provider = new DefaultStatementProvider();
	
	/** the RecordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new DefaultRecordHandlerFactory();
	
	/** the selector */
	private Selector selector = new DefaultSelector();
	
	/** the updater*/
	private Updater updater = new DefaultUpdater();

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
	public void setQueryBuilder(QueryLoader queryBuilder){
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
	 * @see org.coder.alpha.jdbc.service.QueryService#executeCount(org.coder.alpha.jdbc.service.ReadingRequest, java.sql.Connection)
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
	 * @see org.coder.alpha.jdbc.service.QueryService#executeQuery(org.coder.alpha.jdbc.service.ReadingRequest, java.sql.Connection)
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
	 * @see org.coder.alpha.jdbc.service.QueryService#executeFetch(org.coder.alpha.jdbc.service.ReadingRequest, java.sql.Connection)
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
	 * @see org.coder.alpha.jdbc.service.QueryService#executeTotalQuery(org.coder.alpha.jdbc.service.ReadingRequest, java.sql.Connection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> TotalList<T> executeTotalQuery(ReadingRequest param,Connection con) {
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
	 * @see org.coder.alpha.jdbc.service.QueryService#executeUpdate(org.coder.alpha.jdbc.service.ModifyingRequest, java.sql.Connection)
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
	 * @see org.coder.alpha.jdbc.service.QueryService#executeBatch(org.coder.alpha.jdbc.service.BatchParameter, java.sql.Connection)
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
