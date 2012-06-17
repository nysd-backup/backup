/**
 * Copyright 2011 the original author
 */
package sqlengine.facade.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sqlengine.builder.CommentAppender;
import sqlengine.builder.SQLBuilder;
import sqlengine.builder.StatementProvider;
import sqlengine.builder.impl.SQLBuilderProxyImpl;
import sqlengine.builder.impl.StatementProviderImpl;
import sqlengine.exception.ExceptionHandler;
import sqlengine.exception.impl.ExceptionHandlerImpl;
import sqlengine.executer.LazyList;
import sqlengine.executer.RecordHandler;
import sqlengine.executer.RecordHandlerFactory;
import sqlengine.executer.ResultSetHandler;
import sqlengine.executer.Selector;
import sqlengine.executer.Updater;
import sqlengine.executer.impl.RecordHandlerFactoryImpl;
import sqlengine.executer.impl.ResultSetHandlerImpl;
import sqlengine.executer.impl.SelectorImpl;
import sqlengine.executer.impl.UpdaterImpl;
import sqlengine.facade.QueryParameter;
import sqlengine.facade.QueryResult;
import sqlengine.facade.SQLEngineFacade;
import sqlengine.facade.SQLParameter;
import sqlengine.facade.UpdateParameter;



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
	 * @see sqlengine.facade.SQLEngineFacade#executeCount(sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public long executeCount(QueryParameter param,Connection con) {
		
		List<Object> bindList = new ArrayList<Object>();	
		String query = sqlBuilder.setCount(createSQL(param,bindList));
		
		PreparedStatement stmt = null;		
		ResultSet rs = null;

		try{

			stmt = provider.buildStatement(param.getSqlId(),con, query, bindList, param.getTimeoutSeconds(),0,param.getFetchSize());			
			rs= selector.select(stmt);
			
			List<HashMap> decimal = resultSetHandler.getResultList(rs, HashMap.class,null);
			Iterator itr = decimal.get(0).values().iterator();
			itr.hasNext();
			Object value = itr.next();
			if( Number.class.isAssignableFrom(value.getClass())){
				return ((Number)value).longValue();
			}
			throw new IllegalStateException("Illegal type : type = " + value.getClass());
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(stmt);
		}
	}

	/**
	 * @see sqlengine.facade.SQLEngineFacade#executeQuery(sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeQuery(QueryParameter param , Connection con){	
	
		List<Object> bindList = new ArrayList<Object>();	
		String query = createSQL(param,bindList);
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			int maxRows = param.getMaxSize() != 0 ? param.getFirstResult() + param.getMaxSize() : 0;
			stmt = provider.buildStatement(param.getSqlId(),con, query, bindList,param.getTimeoutSeconds(),maxRows,param.getFetchSize());		
			rs = selector.select(stmt);
			resultSetHandler.skip(rs,param.getFirstResult());
			
			return resultSetHandler.getResultList(rs, param.getResultType(),param.getFilter());
			
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * @see sqlengine.facade.SQLEngineFacade#executeFetch(sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeFetch(QueryParameter param,Connection con) {

		List<Object> bindList = new ArrayList<Object>();	
		String query = createSQL(param,bindList);

		ResultSet rs = null;
		PreparedStatement stmt = null;
		try{							
			int maxRows = param.getMaxSize() != 0 ? param.getFirstResult() + param.getMaxSize() : 0;
			stmt = provider.buildStatement(param.getSqlId(),con, query, bindList,param.getTimeoutSeconds(),maxRows,param.getFetchSize());	
			rs = selector.select(stmt);
			resultSetHandler.skip(rs,param.getFirstResult());			
			
			//ResultFetch用オブジェクトに返却
			RecordHandler handler = recordHandlerFactory.create(param.getResultType(), rs);								
			return new LazyList<T>(rs,handler,exceptionHandler);
			
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}
	}

	/**
	 * @see sqlengine.facade.SQLEngineFacade#executeTotalQuery(sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public QueryResult executeTotalQuery(QueryParameter param,Connection con) {
		List<Object> bindList = new ArrayList<Object>();			
		String query = createSQL(param,bindList);
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			
			stmt = provider.buildStatement(param.getSqlId() ,con, query, bindList,param.getTimeoutSeconds(),0,param.getFetchSize());
			rs = selector.select(stmt);
			resultSetHandler.skip(rs,param.getFirstResult());
			
			return resultSetHandler.getResultList(rs, param.getResultType(),param.getFilter(), param.getMaxSize(),param.getFirstResult());
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}

	}

	/**
	 * @see sqlengine.facade.SQLEngineFacade#executeUpdate(sqlengine.facade.UpdateParameter, java.sql.Connection)
	 */
	@Override
	public int executeUpdate(UpdateParameter param, Connection con) {
		
		//SQL生成
		List<Object> bindList = new ArrayList<Object>();			
		String executingSql = createSQL(param,bindList);
		PreparedStatement stmt = null;
		
		try{
			stmt = provider.buildStatement(param.getSqlId(),con, executingSql, bindList,param.getTimeoutSeconds(),0,0);	
			return updater.update(stmt);
		}catch(SQLException sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(stmt);
		}
	}
	
	/**
	 * @see sqlengine.facade.SQLEngineFacade#executeBatch(sqlengine.facade.BatchParameter, java.sql.Connection)
	 */
	@Override
	public int[] executeBatch(List<UpdateParameter> param, Connection con) {
		
		//先頭行で作成されたSQLを使用する
		
		UpdateParameter base = param.get(0);
		
		//SQL生成
		String executingSql = buildSql(base);
		
		List<List<Object>> bindList = new ArrayList<List<Object>>();
		List<Map<String,Object>> parameters = new ArrayList<Map<String,Object>>();
		for(UpdateParameter p : param){
			bindList.add(new ArrayList<Object>());
			parameters.add(p.getParameter());
		}
		executingSql = sqlBuilder.replaceToPreparedSql(executingSql, parameters,bindList,base.getSqlId());						
		PreparedStatement stmt = null;
		
		try{
			//ステートメント
			stmt = provider.createStatement(base.getSqlId(),con, executingSql, base.getTimeoutSeconds(),0,0);	
			
			//バインド変数追加、バッチ実行
			for(int i = 0 ; i < bindList.size(); i++){
				List<Object> bind = bindList.get(i);
				provider.setBindParameter(stmt, bind);
				stmt.addBatch();
			}
			return updater.batchUpdate(stmt);
	
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
	 * @param param the paraemeter
	 * @param bindList the bindList
	 * @return the query
	 */
	@SuppressWarnings("unchecked")
	private String createSQL(SQLParameter param, List<Object> bindList){
		String executingSql = buildSql(param);
		executingSql = sqlBuilder.replaceToPreparedSql(executingSql, Arrays.asList(param.getParameter()), Arrays.asList(bindList),param.getSqlId());				
		return executingSql;	
	}
	
	/**
	 * Builds the SQL.
	 * @param param the parameter
	 * @return the SQL
	 */
	private String buildSql(SQLParameter param){
		String executingSql  = param.getSql();
		if(!param.isUseRowSql()){
			executingSql = sqlBuilder.build(param.getSqlId(), executingSql);		
			executingSql = sqlBuilder.evaluate(executingSql, param.getBranchParameter(),param.getSqlId());
		}				
		if( commentAppender != null){
			executingSql = commentAppender.setExternalString(param, executingSql);
		}
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
		}catch(SQLException sqle){			
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
		}catch(SQLException sqle){				
		}
	}

}
