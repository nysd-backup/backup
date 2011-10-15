/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.facade.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import framework.sqlengine.builder.CommentAppender;
import framework.sqlengine.builder.SQLBuilder;
import framework.sqlengine.builder.StatementProvider;
import framework.sqlengine.builder.impl.SQLBuilderProxyImpl;
import framework.sqlengine.builder.impl.StatementProviderImpl;
import framework.sqlengine.exception.ExceptionHandler;
import framework.sqlengine.exception.impl.ExceptionHandlerImpl;
import framework.sqlengine.executer.LazyList;
import framework.sqlengine.executer.RecordHandler;
import framework.sqlengine.executer.RecordHandlerFactory;
import framework.sqlengine.executer.ResultSetHandler;
import framework.sqlengine.executer.Selector;
import framework.sqlengine.executer.Updater;
import framework.sqlengine.executer.impl.RecordHandlerFactoryImpl;
import framework.sqlengine.executer.impl.ResultSetHandlerImpl;
import framework.sqlengine.executer.impl.SelectorImpl;
import framework.sqlengine.executer.impl.UpdaterImpl;
import framework.sqlengine.facade.QueryParameter;
import framework.sqlengine.facade.QueryResult;
import framework.sqlengine.facade.SQLEngineFacade;
import framework.sqlengine.facade.SQLParameter;
import framework.sqlengine.facade.UpdateParameter;

/**
 * The facade of the SQLEngine
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLEngineFacadeImpl implements SQLEngineFacade{

	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
	
	/** the ResultSetHandler */
	private ResultSetHandler resultSetHandler = new ResultSetHandlerImpl();

	/** the SQLBuilder */
	private SQLBuilder sqlBuilder = new SQLBuilderProxyImpl();
	
	/** the StatementProvider */
	private StatementProvider provider = new StatementProviderImpl();
	
	/** the RecordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** the CommentAppender*/
	private CommentAppender commentAppender = null;
	
	/** the selector */
	private Selector selector = new SelectorImpl();
	
	/** the updater*/
	private Updater updater = new UpdaterImpl();
	
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
	 * @param sqlBuilder the sqlBuilder to set
	 */
	public void setSqlBuilder(SQLBuilder sqlBuilder){
		this.sqlBuilder = sqlBuilder;
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
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeCount(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int executeCount(QueryParameter<?> param,Connection con) {
		
		List<Object> bindList = new ArrayList<Object>();	
		String query = sqlBuilder.setCount(createQuery(param,bindList));
		
		//countの場合、範囲設定無効
		
		PreparedStatement stmt = null;		
		ResultSet rs = null;

		try{
			stmt = provider.createStatement(con, query, bindList,param.getSqlId());	
			rs= selector.select(stmt);
			List<HashMap> decimal = resultSetHandler.getResultList(rs, HashMap.class,null);
			Iterator itr = decimal.get(0).values().iterator();
			itr.hasNext();
			Object value = itr.next();
			if( Number.class.isAssignableFrom(value.getClass())){
				return ((Number)value).intValue();
			}
			throw new IllegalStateException("Illegal type : type = " + value.getClass());
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(stmt);
		}
	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeQuery(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeQuery(QueryParameter<T> param , Connection con){	
		List<Object> bindList = new ArrayList<Object>();	
		String query = createQuery(param,bindList);
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			stmt = provider.createStatement(con, query, bindList,param.getSqlId());
			rs = selector.select(stmt);

			return resultSetHandler.getResultList(rs, param.getResultType(),param.getFilter());
			
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeFetch(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeFetch(QueryParameter<T> param,Connection con) {

		List<Object> bindList = new ArrayList<Object>();	
		String query = createQuery(param,bindList);

		ResultSet rs = null;
		PreparedStatement stmt = null;
		try{							
			stmt = provider.createStatement(con, query, bindList,param.getSqlId());	
			rs = selector.select(stmt);				
			
			//ResultFetch用オブジェクトに返却
			RecordHandler<T> handler = recordHandlerFactory.create(param.getResultType(), rs);								
			return new LazyList<T>(stmt,rs,handler,exceptionHandler);
			
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}
	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeTotalQuery(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> QueryResult<T> executeTotalQuery(QueryParameter<T> param,Connection con) {
		List<Object> bindList = new ArrayList<Object>();	
		
		String query = createSql(param,bindList);
		//最大件数はresultSetを回しながら取得するため取得件数のrange設定しない
		query = sqlBuilder.setRange(query, param.getFirstResult() , 0 ,bindList);
		if( commentAppender != null){
			query = commentAppender.setExternalString(param, query);
		}
		
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			stmt = provider.createStatement(con, query, bindList,param.getSqlId());
			rs = selector.select(stmt);
			return resultSetHandler.getResultList(rs, param.getResultType(),param.getFilter(), param.getMaxSize());
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}

	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeUpdate(framework.sqlengine.facade.UpdateParameter, java.sql.Connection)
	 */
	@Override
	public int executeUpdate(UpdateParameter param, Connection con) {
		
		//SQL生成
		List<Object> bindList = new ArrayList<Object>();			
		String executingSql = createSql(param,bindList);
		
		//コメント追加
		if( commentAppender != null){
			executingSql = commentAppender.setExternalString(param, executingSql);
		}
		
		PreparedStatement stmt = null;
		
		try{
			stmt = provider.createStatement(con, executingSql, bindList,param.getSqlId());	
			return updater.update(stmt);
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(stmt);
		}
	}
	
	/**
	 * Creates the query.
	 * 
	 * @param <T>　the type
	 * @param param the paraemeter
	 * @param bindList the bindList
	 * @return the query
	 */
	private <T> String createQuery(QueryParameter<T> param, List<Object> bindList){
		String executingSql = createSql(param,bindList);
		executingSql = sqlBuilder.setRange(executingSql, param.getFirstResult() , param.getMaxSize(),bindList);
		if( commentAppender != null){
			executingSql = commentAppender.setExternalString(param, executingSql);
		}
		return executingSql;	
	}
	
	/**
	 * Creates the SQL.
	 * 
	 * @param <T>　the type
	 * @param param the paraemeter
	 * @param bindList the bindList
	 * @return the query
	 */
	private String createSql(SQLParameter param, List<Object> bindList){
		String executingSql = param.getSql();
		
		if(!param.isUseRowSql()){
			executingSql = sqlBuilder.build(param.getSqlId(), executingSql);		
			executingSql = sqlBuilder.evaluate(executingSql, param.getBranchParameter(),param.getSqlId());
		}				
		executingSql = sqlBuilder.replaceToPreparedSql(executingSql, param.getParameter(), bindList,param.getSqlId());				
		return executingSql;
	}
	
	/**
	 * Close.
	 * 
	 * @param rs the rs
	 * @param stmt the statement
	 */
	private void close(ResultSet rs , Statement stmt){
		try{
			if(rs != null){
				rs.close();
			}
		}catch(SQLException sqlee){			
		}finally{
			close(stmt);
		}
	}
	
	/**
	 * Close.
	 * 
	 * @param stmt the statement
	 */
	private void close(Statement stmt){
		try{
			if( stmt != null){
				stmt.close();
			}
		}catch(SQLException sqlee){				
		}
	}


}
