/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.facade.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import kosmos.framework.sqlengine.builder.CommentAppender;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.builder.StatementProvider;
import kosmos.framework.sqlengine.builder.impl.SQLBuilderProxyImpl;
import kosmos.framework.sqlengine.builder.impl.StatementProviderImpl;
import kosmos.framework.sqlengine.exception.ExceptionHandler;
import kosmos.framework.sqlengine.exception.impl.ExceptionHandlerImpl;
import kosmos.framework.sqlengine.executer.LazyList;
import kosmos.framework.sqlengine.executer.RecordHandler;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.ResultSetHandler;
import kosmos.framework.sqlengine.executer.Selector;
import kosmos.framework.sqlengine.executer.Updater;
import kosmos.framework.sqlengine.executer.impl.RecordHandlerFactoryImpl;
import kosmos.framework.sqlengine.executer.impl.ResultSetHandlerImpl;
import kosmos.framework.sqlengine.executer.impl.SelectorImpl;
import kosmos.framework.sqlengine.executer.impl.UpdaterImpl;
import kosmos.framework.sqlengine.facade.QueryParameter;
import kosmos.framework.sqlengine.facade.QueryResult;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.SQLParameter;
import kosmos.framework.sqlengine.facade.UpdateParameter;


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
	 * @see kosmos.framework.sqlengine.facade.SQLEngineFacade#executeCount(kosmos.framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int executeCount(QueryParameter param,Connection con) {
		
		List<Object> bindList = new ArrayList<Object>();	
		String query = sqlBuilder.setCount(createSQL(param,bindList));
		
		PreparedStatement stmt = null;		
		ResultSet rs = null;

		try{

			stmt = provider.createStatement(param.getSqlId(),con, query, bindList, param.getTimeoutSeconds(),0,param.getFetchSize());			
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
	 * @see kosmos.framework.sqlengine.facade.SQLEngineFacade#executeQuery(kosmos.framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeQuery(QueryParameter param , Connection con){	
		List<Object> bindList = new ArrayList<Object>();	
		String query = createSQL(param,bindList);
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			stmt = provider.createStatement(param.getSqlId(),con, query, bindList,param.getTimeoutSeconds(),param.getFirstResult()+param.getMaxSize(),param.getFetchSize());
			rs = selector.select(stmt);
			resultSetHandler.skip(rs,param.getFirstResult());
			
			return resultSetHandler.getResultList(rs, param.getResultType(),param.getFilter());
			
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * @see kosmos.framework.sqlengine.facade.SQLEngineFacade#executeFetch(kosmos.framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> List<T> executeFetch(QueryParameter param,Connection con) {

		List<Object> bindList = new ArrayList<Object>();	
		String query = createSQL(param,bindList);

		ResultSet rs = null;
		PreparedStatement stmt = null;
		try{							
			stmt = provider.createStatement(param.getSqlId(),con, query, bindList,param.getTimeoutSeconds(),param.getFirstResult() + param.getMaxSize(),param.getFetchSize());	
			rs = selector.select(stmt);
			resultSetHandler.skip(rs,param.getFirstResult());			
			
			//ResultFetch用オブジェクトに返却
			RecordHandler handler = recordHandlerFactory.create(param.getResultType(), rs);								
			return new LazyList<T>(rs,handler,exceptionHandler);
			
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}
	}

	/**
	 * @see kosmos.framework.sqlengine.facade.SQLEngineFacade#executeTotalQuery(kosmos.framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public QueryResult executeTotalQuery(QueryParameter param,Connection con) {
		List<Object> bindList = new ArrayList<Object>();			
		String query = createSQL(param,bindList);
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			
			stmt = provider.createStatement(param.getSqlId() ,con, query, bindList,param.getTimeoutSeconds(),0,param.getFetchSize());
			rs = selector.select(stmt);
			resultSetHandler.skip(rs,param.getFirstResult());
			
			return resultSetHandler.getResultList(rs, param.getResultType(),param.getFilter(), param.getMaxSize(),param.getFirstResult());
		}catch(Throwable sqle){
			throw exceptionHandler.rethrow(sqle);
		}finally{
			close(rs,stmt);
		}

	}

	/**
	 * @see kosmos.framework.sqlengine.facade.SQLEngineFacade#executeUpdate(kosmos.framework.sqlengine.facade.UpdateParameter, java.sql.Connection)
	 */
	@Override
	public int executeUpdate(UpdateParameter param, Connection con) {
		
		//SQL生成
		List<Object> bindList = new ArrayList<Object>();			
		String executingSql = createSQL(param,bindList);
		PreparedStatement stmt = null;
		
		try{
			stmt = provider.createStatement(param.getSqlId(),con, executingSql, bindList,param.getTimeoutSeconds(),0,0);	
			return updater.update(stmt);
		}catch(Throwable sqle){
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
	private String createSQL(SQLParameter param, List<Object> bindList){
		String executingSql = param.getSql();
		
		if(!param.isUseRowSql()){
			executingSql = sqlBuilder.build(param.getSqlId(), executingSql);		
			executingSql = sqlBuilder.evaluate(executingSql, param.getBranchParameter(),param.getSqlId());
		}				
		executingSql = sqlBuilder.replaceToPreparedSql(executingSql, param.getParameter(), bindList,param.getSqlId());				
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
